package com.pcwk.ehr.join;

import com.pcwk.ehr.cmn.PLog;

public class JoinServiceMain implements PLog {

	JoinService service;
	JoinDao dao;
	JoinDTO dto;
	
	public JoinServiceMain() {
		dao = new JoinDao();
		dto = new JoinDTO("sh","김코코","01020211203","coco1203","","coco1203@naver.com","","coco","","coco");
	}
	
	public void idCheck() {
		log.debug("idCheck()");
		int flag = dao.idCheck(dto);
		
		if(0 != flag) {
			log.debug("아이디 중복:{}",flag);
		}else {
			log.debug("아이디 미사용:{}",flag) ;
		}
		
	}
	
	public static void main(String[] args) {
		JoinServiceMain main = new JoinServiceMain();
		main.idCheck();

	}

}
