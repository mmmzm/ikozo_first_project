package com.pcwk.ehr.mainpage;

import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;

public class MainPageService implements PLog {
	private MainPageDao dao;
	
	public MainPageService() {
		dao = new MainPageDao();
	}

	public List<MainPageDTO> doRetrieveAdimY(DTO search) {
		return dao.doRetrieveAdimY(search);
	}
	
	public List<MainPageDTO> doRetrieveAdimN(DTO search) {
		return dao.doRetrieveAdimN(search);
	}
}
