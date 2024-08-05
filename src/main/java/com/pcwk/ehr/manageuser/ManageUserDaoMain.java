package com.pcwk.ehr.manageuser;

import java.util.Iterator;
import java.util.List;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.SearchDTO;

public class ManageUserDaoMain implements PLog{

	ManageUserDao dao;
	ManageUserDTO board01;
	
	public ManageUserDaoMain() {
		dao = new ManageUserDao();
		
		board01 = new ManageUserDTO(1, "user1", "user1", "N", "없음", "24/06/11", "null", 0, 41, 103, "삐뽀삐뽀 119 소아과");
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
		ManageUserDTO outVO = dao.doSelectOne(board01);
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
		
//		--WHERE title    LIKE :searchWord||'%'     "10" (제목)
//		--WHERE contents LIKE :searchWord||'%'     "20" (내용)
//		--WHERE mod_id      = :searchWord||'%'     "30" (아이디)
//		--WHERE title  LIKE   :searchWord||'%'     "40" (제목+내용)
//		   --OR contents LIKE :searchWord||'%'   
//		--WHERE seq         = :searchWord||'%'     "50" (시퀀스)
		
		// 검색구분
		searchVO.setSearchDiv("50");
		searchVO.setSearchWord("80");
		
		List<ManageUserDTO> list = dao.doRetrieve(searchVO);
		
		int i =0;
		
		for (ManageUserDTO vo :list) {
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
	
	public void doUpdateBack(){
		log.debug("doUpdateBack()");
		int flag = dao.doUpdateBack(board01);
		if (1==flag) {
			log.debug("콜백 성공 : {}", flag);
		}else {
			log.debug("콜백 실패 : {}", flag);			
		}
	} // doUpdateBack 끝 	
	
	public static void main(String[] args) {
		ManageUserDaoMain m = new ManageUserDaoMain();
		//m.doUpdate();
		//m.doUpdateBack();
		//m.doDelete();
		//m.doSelectOne();
		m.doRetrieve();
	} // main

} // class