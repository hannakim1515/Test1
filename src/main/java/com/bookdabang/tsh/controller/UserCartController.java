package com.bookdabang.tsh.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookdabang.common.domain.CartVO;
import com.bookdabang.common.domain.MemberVO;
import com.bookdabang.common.domain.ProductVO;
import com.bookdabang.kmj.service.UserProductService;
import com.bookdabang.ljs.service.LoginService;
import com.bookdabang.tsh.domain.CartProdQttDTO;
import com.bookdabang.tsh.domain.CartSelectDTO;
import com.bookdabang.tsh.domain.CartViewDTO;
import com.bookdabang.tsh.service.CartService;

@RestController
@RequestMapping("/userCart/*")
public class UserCartController {

	@Inject
	public CartService cService;
	@Inject
	public UserProductService pService;
	@Inject
	public LoginService lService;
	
	// 장바구니 개수 
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<Integer> countCart(HttpSession ses) {
		ResponseEntity<Integer> result = null;
		CartSelectDTO dto = new CartSelectDTO();
		try {
			String userId = null;
			String ipaddr = null;
			MemberVO loginMember = lService.findLoginSess((String) ses.getAttribute("sessionId"));
			if (loginMember != null) {
				userId = loginMember.getUserId();
			} else {
				ipaddr = (String) ses.getAttribute("ipAddr");
			}
			dto.setUserId(userId);
			dto.setIpaddr(ipaddr);
			int cntCart = cService.countCart(dto);
			result = new ResponseEntity<Integer>(cntCart, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
		return result;
	}

	// cartNo로 데이터 가져옴
	@RequestMapping(value = "/cartByNo", method = RequestMethod.POST)
	public ResponseEntity<List<CartViewDTO>> getCartByCartNo(@RequestBody List<Integer> cartNo) throws Exception {
		ResponseEntity<List<CartViewDTO>> result = null;
		System.out.println("cartsNo  = " + cartNo);
		List<CartVO> cartLst = cService.selectCartByNo(cartNo);
		List<CartViewDTO> cartView = new ArrayList<CartViewDTO>();
		for (CartVO cart : cartLst) {
			ProductVO product = pService.readProduct(cart.getProductNo());
			CartViewDTO cv = new CartViewDTO(product.getProduct_no(), cart.getCartNo(), product.getTitle(),
					product.getCover(), product.getSell_price(), cart.getProductQtt(), product.getStock());
			cartView.add(cv);
		}
		if (cartLst == null) {
			result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			result = new ResponseEntity<List<CartViewDTO>>(cartView, HttpStatus.OK);
		}
		return result;
	}

	// 모든 카트 가져옴
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<CartViewDTO>> getAllCart(HttpSession ses, String cartsNo) throws Exception {
		ResponseEntity<List<CartViewDTO>> result = null;
		try {
			System.out.println(cartsNo);
			List<CartVO> cartLst = null;
			CartSelectDTO dto = new CartSelectDTO();
			MemberVO loginMember = lService.findLoginSess((String) ses.getAttribute("sessionId"));
			String userId = null;
			String ipaddr = null;
			if (loginMember != null) {
				userId = loginMember.getUserId();
			} else {
				ipaddr = (String) ses.getAttribute("ipAddr");
			}
			dto.setUserId(userId);
			dto.setIpaddr(ipaddr);
			cartLst = cService.getAllCart(dto);
			List<CartViewDTO> cartView = cService.getCartView(cartLst);
			result = new ResponseEntity<List<CartViewDTO>>(cartView, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return result;
	}

	// 카트no로 카트 업데이트
	@RequestMapping(value = "/{cartNo}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateCart(@RequestBody CartProdQttDTO dto) {
		ResponseEntity<String> result = null;
		System.out.println("updateCart 실행");
		try {
			if (cService.updateCart(dto) == 1) {
				result = new ResponseEntity<String>("success", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return result;
	}

	// cartNo로 카트 삭제
	@RequestMapping(value = "/{cartNo}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCart(@PathVariable("cartNo") int cartNo) {
		ResponseEntity<String> result = null;
		System.out.println(cartNo);
		try {
			if (cService.deleteCart(cartNo) == 1) {
				result = new ResponseEntity<String>("success", HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return result;
	}

	// 카트 insert
	@RequestMapping(value = "/addCart", method = RequestMethod.POST)
	public ResponseEntity<String> insertCart(int productNo,int productQtt,HttpSession ses) {
		ResponseEntity<String> result = null;
		System.out.println(productNo);
		try {
			MemberVO loginMember = lService.findLoginSess((String) ses.getAttribute("sessionId"));
			String userId = null;
			String ipaddr = null;
			CartVO cart = new CartVO();
			cart.setProductQtt(productQtt);
			cart.setProductNo(productNo);
			if (loginMember != null) {
				userId = loginMember.getUserId();
				cart.setUserId(userId);
			} else {
				ipaddr = (String) ses.getAttribute("ipAddr");
				cart.setIpaddr(ipaddr);
			}
			
			if (cService.insertCart(cart) == 1) {
				result = new ResponseEntity<String>("success", HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return result;
	}
	
	// 비로그인 장바구니 로그인 장바구니로 update
	@RequestMapping(value = "/loginCart", method = RequestMethod.POST)
	public ResponseEntity<String> loginCart(HttpSession ses){
		ResponseEntity<String> result = null;
		String ipAddr = (String) ses.getAttribute("ipAddr");
		System.out.println(ipAddr);
		String sessionId = (String) ses.getAttribute("sessionId");
		System.out.println(sessionId);
		
		try {
			if(sessionId != null) {
				MemberVO m = lService.findLoginSess(sessionId);
				CartSelectDTO dto = new CartSelectDTO(m.getUserId(), ipAddr);
				if (cService.loginCart(dto) == 1) {
					result = new ResponseEntity<String>("success", HttpStatus.OK);
				}
			}else {
				result = new ResponseEntity<String>("success", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return result;
	}

}
