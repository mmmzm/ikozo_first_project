package com.pcwk.ehr.bfavorite;

import com.pcwk.ehr.cmn.PLog;

public class BFavoriteService implements PLog {
	
	private BFavoriteDAO dao;
	
	public BFavoriteService() {
		dao = new BFavoriteDAO();
	}
	
	public int doFavSave(BFavoriteDTO param) {
		return dao.doSave(param);
	}
	
	public int doDelete(BFavoriteDTO param) {
		return dao.doDelete(param);
	}
}
