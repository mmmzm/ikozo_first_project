package com.pcwk.ehr.favorite;

import java.util.List;

import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.SearchDTO;

public class FavoriteService implements PLog {

	private FavoriteDAO dao;
	public	FavoriteService() {
		dao = new FavoriteDAO();
	}
	
	// 목록 조회
	 private FavoriteDAO favoriteDAO = new FavoriteDAO();
  
	public List<FavoriteDTO> doRetrieve(FavoriteDTO favoriteDTO) {
	    return favoriteDAO.doRetrieve(favoriteDTO);
	}

	// 삭제
	public int doDelete(FavoriteDTO param) {
		return dao.doDelete(param);
	}	

} // class