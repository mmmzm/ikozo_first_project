package com.pcwk.ehr.manageuser;

import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;

public class ManageUserService implements PLog {

	private ManageUserDao dao;
	public	ManageUserService() {
		dao = new ManageUserDao();
	}
	
	// 목록 조회
	public List<ManageUserDTO> doRetrieve(DTO search){
		return dao.doRetrieve(search);
	}
	
	// 저장
	public int doSave(ManageUserDTO param) {
		return dao.doSave(param);
	}
	
	// 수정
	public int doUpdate(ManageUserDTO param) {
		return dao.doUpdate(param);
	}
	
	// 삭제
	public int doDelete(ManageUserDTO param) {
		return dao.doDelete(param);
	}	
	
	// doSelectOne
	public ManageUserDTO doSelectOne(ManageUserDTO param) {
		ManageUserDTO outVO = new ManageUserDTO();
		
		// doSelectOne
		outVO = dao.doSelectOne(param);
		
		return outVO;
	}
} // class