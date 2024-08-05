package com.pcwk.ehr.managebook;

import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;

public class ManageBookService implements PLog {

	private ManageBookDao dao;
	public	ManageBookService() {
		dao = new ManageBookDao();
	}
	
	// 목록 조회
	public List<ManageBookDTO> doRetrieve(DTO search){
		return dao.doRetrieve(search);
	}
	
	// 저장
	public int doSave(ManageBookDTO param) {
		return dao.doSave(param);
	}
	
	// 수정
	public int doUpdate(ManageBookDTO param) {
		return dao.doUpdate(param);
	}

	// 대출 연장
	public int doDueDateUpdate(ManageBookDTO param) {
		return dao.doDueDateUpdate(param);
	}

	// 반납
	public int doReturned(ManageBookDTO param) {
		return dao.doReturned(param);
	}
	
	// 삭제
	public int doDelete(ManageBookDTO param) {
		return dao.doDelete(param);
	}	
	
	// doSelectOne
	public ManageBookDTO doSelectOne(ManageBookDTO param) {
		ManageBookDTO outVO = new ManageBookDTO();
		
		// doSelectOne
		outVO = dao.doSelectOne(param);
		
		return outVO;
	}	
} // class