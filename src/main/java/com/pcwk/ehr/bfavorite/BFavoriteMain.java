package com.pcwk.ehr.bfavorite;

import com.pcwk.ehr.cmn.PLog;

public class BFavoriteMain implements PLog {

	BFavoriteDAO dao;
	BFavoriteDTO fav;
	
	public BFavoriteMain() {
		dao = new BFavoriteDAO();
		
		fav = new BFavoriteDTO(10,"admin2",322,"SYSDATE");
	}
	
	public void doSave() {
		log.debug("do save()");
		int flag = dao.doSave(fav);
		if(1 == flag) {
			log.debug("성공 : {}", flag);
		}else 
			log.debug("실패 : {} ", flag);
	}
	
	public void doDelete() {
		
		int flag = dao.doDelete(fav);
		
		if(1 == flag) {
			log.debug("삭제 성공 : {}", flag);
		}else
			log.debug("삭제 실패 : {}", flag);
	}
	
	public static void main(String[] args) {
		BFavoriteMain f = new BFavoriteMain();
		f.doSave();
		//f.doDelete();
	}

}
