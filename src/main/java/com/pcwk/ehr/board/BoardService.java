package com.pcwk.ehr.board;

import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.board.BoardDTO;

public class BoardService implements PLog {

	private BoardDao dao;
	public	BoardService() {
		dao = new BoardDao();
	}
	
	// 목록 조회

	public List<BoardDTO> doRetrieve(DTO search){
		return dao.doRetrieve(search);
	}
	
	public List<BoardDTO> getAdminBoardList(String isAdmin) {
	    return dao.getAdminBoardList(isAdmin);
	}

	
	// 저장
	public int doSave(BoardDTO param) {
		return dao.doSave(param);
	}
	
	// 수정
	public int doUpdate(BoardDTO param) {
		return dao.doUpdate(param);
	}
	
	// 삭제
	public int doDelete(BoardDTO param) {
		return dao.doDelete(param);
	}	
	
    // 조회수 증가
    public int doUpdateReadcnt(BoardDTO param) {
        return dao.doUpdateReadcnt(param);
    }

    // 게시글 상세 조회
    public BoardDTO getBoardDetail(int seq) {
        return dao.getBoardDetail(seq);
    }
}