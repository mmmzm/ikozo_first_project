package com.pcwk.ehr.answer;

import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.answer.AnswerDAO;

public class AnswerService implements PLog {

	private AnswerDAO dao;
	public	AnswerService() {
		dao = new AnswerDAO();
	}
	
	// 목록 조회
    public List<AnswerDTO> doRetrieve(DTO search) {
        return dao.doRetrieve(search);
    }
	
	// 저장
	public int doSave(AnswerDTO param) {
		return dao.doSave(param);
	}
	
	// 수정
	public int doUpdate(AnswerDTO param) {
		return dao.doUpdate(param);
	}
	
	// 삭제
	public int doDelete(AnswerDTO param) {
		return dao.doDelete(param);
	}

    public List<AnswerDTO> listAnswers(int boardSeq) {
        return dao.retrieveAnswers(boardSeq);
    }

    public List<AnswerDTO> retrieveAnswers(int boardSeq) {
        return dao.retrieveAnswers(boardSeq);
    }

    public void createAnswer(AnswerDTO answerDTO) {
        dao.doSave(answerDTO);
    }

    
    
	
}