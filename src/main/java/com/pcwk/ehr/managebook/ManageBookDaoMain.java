package com.pcwk.ehr.managebook;

import java.util.Iterator;
import java.util.List;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.SearchDTO;

public class ManageBookDaoMain implements PLog{

	ManageBookDao dao;
	ManageBookDTO board01;
	
	public ManageBookDaoMain() {
		dao = new ManageBookDao();
		
		board01 = new ManageBookDTO(1, 321, "TEST1", 10, "소설", "AUTHOR1_2", "COMPANY", "Y", "1234567890123l", "24/06/12", "INTRO_2", "없음", "없음", "없음", "Y", 41, "admin2", "SYSDATE", "admin2", "SYSDATE");
		// board01 = new manage2DTO(도서번호, "제목", "장르", "장르코드", "장르명", "작가", "출판사", "대출가능여부", ISBN[long], 출판일, 도서소개, "대출일", "반납예정일", "반납일", "대출연장카운트", "대출코드", "등록자", "등록일", "수정자", "수정일");
	}
	
	public void doSave(){
		log.debug("doSave()");
		int flag = dao.doSave(board01);
		if (1==flag) {
			log.debug("성공 : {}", flag);
		}else {
			log.debug("실패 : {}", flag);			
		}
	} // doSave끝 
	
	public void doDelete(){
		log.debug("doDelete()");
		int flag = dao.doDelete(board01);
		if (1==flag) {
			log.debug("삭제 성공 : {}", flag);
		}else {
			log.debug("삭제 실패 : {}", flag);			
		}
	} // doDelete끝 	 		
	
	public void doSelectOne(){
		log.debug("doSelectOne()");
		ManageBookDTO outVO = dao.doSelectOne(board01);
		if (null != outVO) {
			log.debug("검색 성공 : {}", outVO);
		}else {
			log.debug("검색 실패 : {}", outVO);			
		}
	} // doSelectOne끝 	
	
	public void doRetrieve() {
		log.debug("doRetrieve()");
		SearchDTO searchVO = new SearchDTO();
		searchVO.setPageNo(1);
		searchVO.setPageSize(10);
		
		// 검색구분
//		searchVO.setSearchDiv("50");
//		searchVO.setSearchWord("80");
		
		List<ManageBookDTO> list = dao.doRetrieve(searchVO);
		
		int i =0;
		
		for (ManageBookDTO vo :list) {
			log.debug("i: {}, vo: {}", ++i, vo);
		}
	} // doRetrieve 끝
	
	public void doUpdate(){
		log.debug("doUpdate()");
		int flag = dao.doUpdate(board01);
		if (1==flag) {
			log.debug("업데이트 성공 : {}", flag);
		}else {
			log.debug("업데이트 실패 : {}", flag);			
		}
	} // doUpdate 끝 	
	
	public void doDueDateUpdate(){
		log.debug("doDueDateUpdate()");
		int flag = dao.doDueDateUpdate(board01);
		if (1==flag) {
			log.debug("연장 성공 : {}", flag);
		}else {
			log.debug("연장 실패 : {}", flag);			
		}
	} // doUpdate 끝 	
	
	public static void main(String[] args) {
		ManageBookDaoMain m = new ManageBookDaoMain();
		//m.doDueDateUpdate();
		//m.doUpdate();
		//m.doDelete();
		m.doSelectOne();
		//m.doRetrieve();
		//m.doSave();
	} // main

} // class