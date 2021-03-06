package com.bookdabang.ljs.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bookdabang.common.domain.AttachFileVO;
import com.bookdabang.common.domain.BoardSearch;
import com.bookdabang.common.domain.CustomerService;
import com.bookdabang.common.domain.PagingInfo;
import com.bookdabang.ljs.domain.CSUploadFile;
import com.bookdabang.ljs.persistence.CSBoardDAO;



@Service
public class CSBoardServiceImpl implements CSBoardService {
	
	@Inject
	CSBoardDAO bdao;

	@Override
	public Map<String, Object> showEntireCSBoard(int pageNo, BoardSearch searchWord) throws Exception {
		
		PagingInfo pi = pagingProcess(pageNo, searchWord);
		List<CustomerService> csBoard = null;
		
		if (searchWord.getSearchWord().equals("") || searchWord.getSearchType().equals("")) {
			csBoard = bdao.showEntireCSBoard(pi);
			
		} else {
			csBoard = bdao.showEntireCSBoard(pi, searchWord);
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("csBoard", csBoard);
		map.put("pagingInfo", pi);
		
		return map;
	}
	
	
	private PagingInfo pagingProcess(int pageNo, BoardSearch searchWord) {
		PagingInfo pi = new PagingInfo();
		System.out.println("페이지 프로세스 : "+ searchWord);
		try{
			if(searchWord.getSearchWord().equals("") || searchWord.getSearchType().equals("")) {
				int cnt = bdao.getTotalPost();
				pi.setTotalPostCnt(bdao.getTotalPost());
				System.out.println("검색결과 갯수 : "+cnt);
			} else {
				int cnt = bdao.getSearchResultCnt(searchWord);
				System.out.println("검색결과 갯수 : "+cnt);
				pi.setTotalPostCnt(bdao.getSearchResultCnt(searchWord));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		pi.setPostPerPage(10);
		
		//pi.setPageCntPerBlock(5);
		
		pi.setTotalPage(pi.getTotalPostCnt());
		
		pi.setStartNum(pageNo);
		
		pi.setTotalPagingBlock(pi.getTotalPage());
		
		pi.setCurrentPagingBlock(pageNo);
		
		pi.setStartNoOfCurPagingBlock(pi.getCurrentPagingBlock());
		
		pi.setEndNoOfCurPagingBlock(pi.getStartNoOfCurPagingBlock());
		
		System.out.println(pi.toString());
		return pi;
	
	}

	@Override
	public Map<String, Object> readCSBoard(int postNo) throws Exception, IOException {
		  
		// 글 읽어오기
		CustomerService csPost =  bdao.readCSPost(postNo);
		
		// 첨부파일 가져오기
		List<AttachFileVO> fileList = bdao.getAttachFiles(postNo);
		
		if (fileList.size() == 0) {
			fileList = null;
		}
		
		Map<String, Object> csMap = new HashMap<String, Object>();
		csMap.put("csPost", csPost);
		csMap.put("fileList", fileList);
		// 여기서, 리턴 값을 두 개 보내야하는데 이 두 개를 어떻게 보내냐? -> 하나에 담아서 보낸다.
		// HOW? Collection 으로. Map 으로 보내자.
		return csMap;
		
	}


	@Override
	public boolean writeCSPost(CustomerService csPost, List<CSUploadFile> upfileLst) throws Exception {
		
		boolean result = false;
		int ref = bdao.getNextNo() + 1;
		System.out.println("write CS 서비스 단에서 가져온 글번호 max : " + ref);
		int resultInsert = bdao.writeCSPost(csPost);
		int fileResult = 0;
		
		CSUploadFile csfile = new CSUploadFile();
		
		//csPost.setContents(csPost.getContents().replace("\r\n","<br />")); // 줄바꿈 적용
		
		
		for (CSUploadFile file : upfileLst) {
			
			csfile.setCsBoardNo(ref);
			csfile.setOriginFile(file.getOriginFile());
			csfile.setThumbnailFile(file.getThumbnailFile());
			csfile.setNotImageFile(file.getNotImageFile());
			
			System.out.println("파일 디비로 가져가는 놈" + csfile.toString());
			fileResult = bdao.insertAttachFile(csfile);
			
		}
		
		if (resultInsert == 1 && fileResult==1) {
			result = true;
		}
		
		return result;
	}

	@Override
	public int deleteCSPost(int postNo) throws Exception {
		 
		return bdao.deleteCSPost(postNo);
	}

	@Override
	public int deleteAttach(int postNo) throws Exception {
	
		return bdao.deleteAttach(postNo);
	}

}
