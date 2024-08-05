package com.pcwk.ehr.board;

import java.util.List;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.SearchDTO;

public class BoardDaoMain implements PLog {

    BoardDao dao;
    BoardDTO board01;

    public BoardDaoMain() {
        dao = new BoardDao();

        board01 = new BoardDTO(1, "TEST_TITLE", 0, "TEST_CONTENTS", "admin", null, "admin", null, "Y");
        // board01 = new BoardDTO(seq, title, readCnt, contents, regId, regDt, modId, modDt, isAdmin);
    }

    public void doSave() {
        log.debug("doSave()");
        int flag = dao.doSave(board01);
        if (1 == flag) {
            log.debug("Save success : {}", flag);
        } else {
            log.debug("Save failure : {}", flag);
        }
    }

    public void doDelete() {
        log.debug("doDelete()");
        int flag = dao.doDelete(board01);
        if (1 == flag) {
            log.debug("Delete success : {}", flag);
        } else {
            log.debug("Delete failure : {}", flag);
        }
    }

    public void doSelectOne() {
        log.debug("doSelectOne()");
        BoardDTO outVO = dao.doSelectOne(board01);
        if (null != outVO) {
            log.debug("Select success : {}", outVO);
        } else {
            log.debug("Select failure : {}", outVO);
        }
    }

	public void doRetrieve() {
		log.debug("doRetrieve()");
		SearchDTO searchVO = new SearchDTO();
		searchVO.setPageNo(1);
		searchVO.setPageSize(10);

	
		searchVO.setSearchDiv("50");
		searchVO.setSearchWord("80");
		
		List<BoardDTO> list = dao.doRetrieve(searchVO);
		
		int i =0;
		
		for (BoardDTO vo :list) {
			log.debug("i: {}, vo: {}", ++i, vo);
		}
	}

	public void doUpdate(){
		log.debug("doUpdate()");
		// title,content,modid 뒤에 _u추가
			String updateStr = "_U";		
			board01.setTitle(board01.getTitle()+updateStr);
			board01.setContents(board01.getContents()+updateStr);
			board01.setModId(board01.getModId()+updateStr);
		// title,content,modid 뒤에 _u추가 끝
		int flag = dao.doUpdate(board01);
		if (1==flag) {
			log.debug("업데이트 성공 : {}", flag);
		}else {
			log.debug("업데이트 실패 : {}", flag);			
		}
	}

	//조회수
	public void doUpdateReadcnt(){
		log.debug("doUpdateReadcnt()");
		int flag = dao.doUpdateReadcnt(board01);
		if (1==flag) {
			log.debug("조회수 증가 : {}", flag);
		}else {
			log.debug("조회수 실패 : {}", flag);			
		}
	}
	


    public static void main(String[] args) {
        BoardDaoMain m = new BoardDaoMain();
        m.doSave();
        m.doRetrieve();
        m.doSelectOne();
        m.doUpdate();
        m.doDelete();
        m.doUpdateReadcnt();
    }

}
