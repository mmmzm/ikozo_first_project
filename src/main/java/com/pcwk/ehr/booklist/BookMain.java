package com.pcwk.ehr.booklist;

import java.util.List;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.SearchDTO;

public class BookMain implements PLog {
	
	BookDAO dao;
	BookDTO book;
	
	public BookMain() {
		dao = new BookDAO();
		
		book = new BookDTO(322,20,"테스트용",0,"24/06/12","에이콘",
				"미상","테스트용입니다.","admin1","SYSDATE","admin1","SYSDATE");
	}
		
	public void doSave() {
		log.debug("do save()");
		int flag = dao.doSave(book);
		if(1 == flag) {
			log.debug("성공 : {}", flag);
		}else 
			log.debug("실패 : {} ", flag);
	}
	
	public void doUpdate() {
		log.debug("doUpdate()");
		String updateStr = "_V";
		book.setBookGenre(book.getBookGenre());
		book.setBookName(book.getBookName()+updateStr);
		book.setIsbn(book.getIsbn());
		book.setBookPubDate(book.getBookPubDate());
		book.setPublisher(book.getPublisher() + updateStr);
		book.setAuthor(book.getAuthor() + updateStr);
		book.setBookInfo(book.getBookInfo() + updateStr);
		book.setModId(book.getModId() +updateStr);
		book.setModDt(book.getModDt());
		book.setBookCode(book.getBookCode());
		
		int flag = dao.doUpdate(book);
		
		if(1 == flag) {
			log.debug("업데이트 성공 : {}", flag);
		}else 
			log.debug("업데이트 실패 : {}", flag);

	}
	
	public void doDelete() {
		log.debug("doDelete()");
		
		int flag = dao.doDelete(book);
		
		if(1 == flag) {
			log.debug("삭제 성공 : {}", flag);
		}else
			log.debug("삭제 실패 : {}", flag);
	}
	
	public void doSelect() {
		log.debug("doSelect()");
		BookDTO outVO = dao.doSelect(book);
		if(null != outVO) {
			log.debug("단건조회 성공 : {}", outVO);
		}else
			log.debug("단건조회 실패 : {} ", outVO);
	}
	
	public void doRetrieve() {
		log.debug("doRetrieve()");
		SearchDTO searchVO = new SearchDTO();
		searchVO.setPageNo(1);
		searchVO.setPageSize(10);
		
		//검색구분
		searchVO.setSearchDiv("10");
		searchVO.setSearchWord("원피스");
		
		List<BookDTO> list = dao.doRetrieve2(searchVO);
		
		int i = 0;
		for(BookDTO vo : list) {
			log.debug("i : {}, vo : {}", ++i, vo);
		}
	}
	
	public static void main(String[] args) {
		BookMain b = new BookMain();
		//b.doSave();
		//b.doUpdate();
		//b.doDelete();
		//b.doSelect();
		b.doRetrieve();
		
	}

}
