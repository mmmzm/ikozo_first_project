package com.pcwk.ehr.favorite;

import java.util.List;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.board.BoardDTO;
import com.pcwk.ehr.cmn.PLog;
	
public class FavoriteMain implements PLog {
    FavoriteDAO dao;
    FavoriteDTO dto;
    

    public FavoriteMain() {
        dao = new FavoriteDAO();
    }

    
	public void doDelete(){
		log.debug("doDelete()");
		int flag = dao.doDelete(dto);
		if (1==flag) {
			log.debug("삭제 성공 : {}", flag);
		}else {
			log.debug("삭제 실패 : {}", flag);			
		}
	} 
	
	
	public void doRetrieve() {
	    log.debug("doRetrieve()");
	    
	    // 검색 조건 설정
	    SearchDTO searchVO = new SearchDTO();	    
	    searchVO.setPageNo(1);
	    searchVO.setPageSize(10);
	    searchVO.setSearchDiv("book_name"); // 예를 들어 book_name으로 설정
	    searchVO.setSearchWord("나는");
	    
	    // 즐겨찾기 목록 조회
	    List<FavoriteDTO> list = dao.doRetrieve(searchVO);
	    
	    // 조회 결과 확인
	    if (list != null && !list.isEmpty()) {
	        int i = 0;
	        for (FavoriteDTO vo : list) {
	            log.debug("i: {}, vo: {}", ++i, vo);
	        }
	    } else {
	        log.debug("조회된 즐겨찾기 목록이 없습니다.");
	    }
	}

    
    
    public void doSelectOne(){
		log.debug("doSelectOne()");
		FavoriteDTO outVO = dao.doSelectOne(dto);
		if (null != outVO) {
			log.debug("검색 성공 : {}", outVO);
		}else {
			log.debug("검색 실패 : {}", outVO);			
		}
	} // doSelectOne끝 	

    public static void main(String[] args) {
        FavoriteMain m = new FavoriteMain();
        m.doRetrieve();
        //m.doDelete();
        //m.doSelectOne();
        
    }
}