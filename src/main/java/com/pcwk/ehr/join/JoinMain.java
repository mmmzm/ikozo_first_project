package com.pcwk.ehr.join;

import com.pcwk.ehr.cmn.PLog;

public class JoinMain implements PLog{
	JoinDao dao;
	JoinDTO dto;

	public JoinMain() {
		dao = new JoinDao();
		dto = new JoinDTO("coco","김코코","01020211203","coco1203","","coco1203@naver.com","","coco","","coco");
	}
	
	public void doSave() {
		log.debug("doSave()");
		int flag = dao.doSave(dto);
		
		if(1==flag) {
			log.debug("등록 성공:{}",flag);
		}else {
			log.debug("등록 실패:{}",flag) ;
			
		}
	}
	
	public void doUpdate() {
		log.debug("doUpdate()");
		String updateStr = "_U";
		
		dto.setUserTel(dto.getUserTel()+updateStr);
		dto.setUserPw(dto.getUserPw()+updateStr);
		dto.setUserEmail(dto.getUserEmail()+updateStr);
		dao.doUpdate(dto);
		
		int flag = dao.doUpdate(dto);
		
		if(1==flag) {
			log.debug("수정 성공:{}",flag);
		}else {
			log.debug("수정 실패:{}",flag) ;
		}
		
	}
	
	public void doSelectOne() {
		log.debug("doSelectOne()");
		JoinDTO outVO = dao.doSelectOne(dto);
		
		if(null != outVO) {
			log.debug("단건조회 성공:{}",outVO);
		}else {
			log.debug("단건조회 실패:{}",outVO) ;
		}
		
	}
	
	public void doDelete(){
		log.debug("doDelete()");
		int flag = dao.doDelete(dto);
		
		if(1==flag) {
			log.debug("삭제 성공:{}",flag);
		}else {
			log.debug("삭제 실패:{}",flag) ;
		}
	}
	
	public static void main(String[] args) {
		JoinMain m = new JoinMain();
		//m.doSave();
		//m.doUpdate();
		//m.doSelectOne();
		m.doDelete();

	}

}
