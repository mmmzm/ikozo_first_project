package com.pcwk.ehr.genre;

import java.util.List;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.SearchDTO;

public class GenreMain implements PLog {

	GenreDAO dao;
	GenreDTO genre01;
	
	public GenreMain() {
		dao = new GenreDAO();
		
		genre01 = new GenreDTO(130,"전공서적");
	}
	
	public void doSave() {
		log.debug("doSave()");
		int flag = dao.doSave(genre01);
		if(1 == flag) {
			log.debug("성공 : {}" ,flag);
		}else {
			log.debug("실패 : {}" ,flag);
		}
	}
	
	public void doUpdate() {
		log.debug("do Update()");
		String updateStr = "_I";
		genre01.setBookgenre(genre01.getBookgenre());
		genre01.setGenreName(genre01.getGenreName() + updateStr);
		int flag = dao.doUpdate(genre01);
		
		if(1 == flag) {
			log.debug("업데이트 성공 : {}", flag);
		}else 
			log.debug("업데이트 실패 : {}", flag);
	}
	
	public void doDelete() {
		log.debug("do delete");
		
		int flag = dao.doDelete(genre01);
		
		if (1 == flag) {
			log.debug("삭제 성공 : {}", flag);
		}else
			log.debug("삭제 실패 : {}", flag);
	}
	
	public void doSelect() {
		log.debug("doSelect");
		GenreDTO outVO = dao.doSelectOne(genre01);
		if(null != outVO) {
			log.debug("단건조회 성공 : {}", outVO);
		}else
			log.debug("단건조회 실패 : {}", outVO);
	}
	
	public void doRetrieve() {
		log.debug("doRetrieve");
		SearchDTO searchVO = new SearchDTO();
		searchVO.setPageNo(1);
		searchVO.setPageSize(10);
		
		searchVO.setSearchDiv("40");
		searchVO.setSearchWord("건강");
		
		List<GenreDTO> list = dao.doRetrieve(searchVO);
		
		int i = 0;
		for(GenreDTO vo : list) {
			log.debug("i : {}, vo : {}", ++i, vo);
		}
	}
	
	
	public static void main(String[] args) {
		GenreMain g = new GenreMain();
		//g.doSave();
		//g.doUpdate();
		//g.doDelete();
		//g.doSelect();
		g.doRetrieve();
	}

}
