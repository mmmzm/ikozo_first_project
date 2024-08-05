package com.pcwk.ehr.mypage;

import java.util.List;


import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;

//Service = 기능이 두개 묶여 있는 것들 짜기
public class MypageService implements PLog {

	private LoanListDao dao;

	
	public MypageService() {
		dao = new LoanListDao();

	}
	
	/**
	 * 마이페이지 대출목록 조회
	 * 
	 * @param search
	 * @return List<BoardDTO>
	 */
	public List<LoanListDTO> doRetrieve(DTO search) {
		return dao.doRetrieve(search);
	}
	
	
}
