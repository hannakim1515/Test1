package com.bookdabang.common.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bookdabang.common.domain.CategoryVO;
import com.bookdabang.common.domain.MemberVO;
import com.bookdabang.common.domain.PagingInfo;
import com.bookdabang.common.domain.ProductVO;
import com.bookdabang.cyh.domain.SearchCriteria;
import com.bookdabang.cyh.domain.UpdateProdDTO;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Inject
	private SqlSession ses; // SqlSessionTemplete 객체 주입

	private static String ns = "com.bookdabang.mapper.productMapper"; // mapper의 namespace

	// 최윤호

	@Override
	public List<CategoryVO> getCategory() throws Exception {
		return ses.selectList(ns + ".getCategory");
	}

	@Override
	public int conditionProdCnt(SearchCriteria sc) throws Exception {
		return ses.selectOne(ns + ".conditionProdCnt", sc);
	}

	@Override
	public List<ProductVO> conditionProdView(SearchCriteria sc, PagingInfo pi) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("searchType", sc.getSearchType());
		param.put("searchWord", sc.getSearchWord());
		param.put("category_code", sc.getCategory_code());
		param.put("startNum", pi.getStartNum());
		param.put("postPerPage", pi.getPostPerPage());
		param.put("sortWord", sc.getSortWord());
		param.put("sortMethod", sc.getSortMethod());
		param.put("startRgDate", sc.getStartRgDate());
		param.put("endRgDate", sc.getEndRgDate());
		param.put("display_status", sc.getDisplay_status());
		param.put("sales_status", sc.getSales_status());
		return ses.selectList(ns + ".conditionProdView", param);
	}

	@Override
	public ProductVO selectProdView(String isbn) throws Exception {

		return ses.selectOne(ns + ".selectProdView", isbn);
	}

	@Override
	public int updateProd(UpdateProdDTO prod) throws Exception {

		return ses.update(ns + ".updateProd", prod);
	}

	@Override
	public int validationProdNo(String prodNo) throws Exception {
		
		return ses.selectOne(ns + ".validationProdNo", prodNo);
	}
	
	//========================= QnA ===================================================
	@Override
	public MemberVO validSession(String sessionId) throws Exception {
		
		return ses.selectOne(ns + ".validSession", sessionId);
	}
	

	// 강명진

	

	@Override
	public List<ProductVO> selectAllProducts() throws Exception {
		return ses.selectList(ns + ".selectAllProducts");
	}

	@Override
	public ProductVO selectProduct(int prodNo) throws Exception {
		return ses.selectOne(ns + ".selectProduct", prodNo);
	}

	@Override
	public List<ProductVO> selectTopProducts(int category) throws Exception {
		return ses.selectList(ns + ".selectTopProducts", category);
	}

}
