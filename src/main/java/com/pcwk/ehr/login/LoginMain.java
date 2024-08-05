package com.pcwk.ehr.login;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;

public class LoginMain implements PLog {

	LoginDAO dao;
	LoginDTO member01;
	
	
	public LoginMain() {
		dao = new LoginDAO();
		member01=new LoginDTO();
		member01.setUserId("user1");
		member01.setUserPw("1111");
	}
	
	public void checkLogin() {
		log.debug("idCheck()");
		int flag = dao.checkLogin(member01);
		if(1==flag) {
			log.debug("idCheck 성공 :{}",flag);
		}else {
			log.debug("idCheck 실패 :{}",flag);
		}
		
	}
	
	public void checkUsedPw() {
		log.debug("idPasswordCheck()");
		int flag = dao.checkUsedPw(member01);
		if(1==flag) {
			log.debug("idPasswordCheck 성공 :{}",flag);
		}else {
			log.debug("idPasswordCheck 실패 :{}",flag);
		}		
	}
	
	public void doSelectOne() {
		log.debug("doSelectOne()");
		LoginDTO outVO=dao.doSelectOne(member01);
		if(null != outVO) {
			log.debug("doSelectOne 성공 :{}",outVO);
		}else {
			log.debug("doSelectOne 실패 :{}",outVO);
		}
		
		
	}
	
	public int login() {
		//id Check > 0
		int result = 0;
		int flag = dao.checkLogin(member01);
		
		if(flag == 0) {
			result = 10;//ID 불일치
			return result;
		}
		
		//id/pass Check >0
		flag = dao.checkUsedPw(member01);
		if(flag == 0) {
			result = 20;//비번 불일치
			return result;
		}
		
		
		return 30;//로그인
		
	}
	
	public DTO doMemberSelect() {
		int result = login();
		LoginDTO outVO = null;
		if( 30 == result ) {
			outVO = dao.doSelectOne(member01);
		}
		
		return outVO;
	}
	
	public static void main(String[] args) {
		LoginMain main = new LoginMain();
		//main.idCheck();
		//main.idPasswordCheck();
		//main.doSelectOne();
		int result = main.login();
		log.debug("result  :{}",result);
		
		LoginDTO Login = (LoginDTO) main.doMemberSelect();
		log.debug("Login  :{}",Login);
	}

}