package com.pcwk.ehr.join;

import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;

public class JoinService implements PLog {
	private JoinDao dao;
	
	public JoinService() {
		dao = new JoinDao();
	}
	
	/**
	 * 회원가입
	 * @param param
	 * @return 성공(1)/실패(0)
	 */
	public int doSave(JoinDTO param) {
		return dao.doSave(param);
	}
	
	
	/**
	 * 회원아이디 중복 조회
	 * @param param
	 * @return 1(존재)/0
	 */
	public int idCheck(JoinDTO param) {
		return dao.idCheck(param);
	}
}
