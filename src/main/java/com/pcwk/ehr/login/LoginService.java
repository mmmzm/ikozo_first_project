package com.pcwk.ehr.login;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.cmn.PLog;

public class LoginService implements PLog {

	private LoginDAO dao;
	
	
	public LoginService() {	
		dao = new LoginDAO();
	}
	
	public int checkUsedId(LoginDTO param) {
		return dao.checkUsedId(param);
	}
	
	/**
	 * 10 : id불일치
	 * 20 : 비번 불일치
	 * 30 : id/비번 일치
	 * @param param
	 * @return 10/20/30
	 */
	public int loginStatus(LoginDTO param) {
		//id Check > 0
		int result = 0;
		int flag = dao.checkUsedId(param);
		
		if(flag == 0) {
			result = 10;//ID 불일치
			return result;
		}
		
		//id/pass Check >0
		flag = dao.checkUsedPw(param);
		if(flag == 0) {
			result = 20;//비번 불일치
			return result;
		}
		
		return 30;//로그인		
	}
	public DTO doMemberSelect(LoginDTO param) {
		return null;
	    
	}

	   public LoginDTO doSelectOne(LoginDTO param) {
		   LoginDTO outVO = new LoginDTO();
		      
		      outVO = dao.doSelectOne(param);
		            
		      return outVO;
		   }

	
	
}
