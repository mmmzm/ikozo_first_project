package com.pcwk.ehr.answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pcwk.ehr.cmn.ConnectionMaker;
import com.pcwk.ehr.cmn.DBUtil;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.cmn.SearchDTO;

public class AnswerDAO implements WorkDiv<AnswerDTO>, PLog {

	private ConnectionMaker connectionMaker;
	
	public AnswerDAO() {
		connectionMaker = new ConnectionMaker();
	}

	
    // doRetrieve 메서드 구현
    public List<AnswerDTO> doRetrieve(int seq) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT SEQ, BOARD_SEQ, CONTENTS, REG_ID, REG_DT, MOD_ID, MOD_DT ");
        sb.append("FROM ANSWER ");
        sb.append("WHERE SEQ = ? ");
        sb.append("ORDER BY MOD_DT DESC ");

        List<AnswerDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection(); // getConnection 메서드는 Connection을 획득하는 방법에 맞게 구현해야 합니다.
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, seq);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                AnswerDTO dto = new AnswerDTO();
                dto.setSeq(rs.getInt("SEQ"));
                dto.setBoardSeq(rs.getInt("BOARD_SEQ"));
                dto.setContents(rs.getString("CONTENTS"));
                dto.setRegId(rs.getString("REG_ID"));
                dto.setRegDt(rs.getDate("REG_DT"));
                dto.setModId(rs.getString("MOD_ID"));
                dto.setModDt(rs.getDate("MOD_DT"));
                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	DBUtil.close(conn, pstmt, rs); // close 메서드는 Connection, PreparedStatement, ResultSet을 닫는 방법에 맞게 구현해야 합니다.
        }

        return list;
    }
    
    // 댓글 목록 조회 메서드
    public List<AnswerDTO> retrieveAnswers(int boardSeq) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.SEQ, a.BOARD_SEQ, a.CONTENTS, a.REG_ID, a.REG_DT, a.MOD_ID, a.MOD_DT ");
        sb.append("FROM ANSWER a ");
        sb.append("JOIN BOARD_BOOK b ON a.BOARD_SEQ = b.SEQ ");
        sb.append("WHERE b.SEQ = ? ");
        sb.append("ORDER BY a.MOD_DT DESC ");

        List<AnswerDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.getConnection(); // Connection 획득 메서드 호출
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, boardSeq);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                AnswerDTO dto = new AnswerDTO();
                dto.setSeq(rs.getInt("SEQ"));
                dto.setBoardSeq(rs.getInt("BOARD_SEQ"));
                dto.setContents(rs.getString("CONTENTS"));
                dto.setRegId(rs.getString("REG_ID"));
                dto.setRegDt(rs.getDate("REG_DT"));
                dto.setModId(rs.getString("MOD_ID"));
                dto.setModDt(rs.getDate("MOD_DT"));
                list.add(dto);
            }

        } catch (SQLException e) {
            log.error("댓글 목록 조회 중 오류 발생: {}", e.getMessage());
        } finally {
        	DBUtil.close(conn, pstmt, rs); // Connection 닫는 메서드 호출
        }

        return list;
    }
    
    // Answer 테이블에 댓글 추가
    @Override
    public int doSave(AnswerDTO answerDTO) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ANSWER (SEQ, BOARD_SEQ, CONTENTS, REG_ID, REG_DT, MOD_ID, MOD_DT) ");
        sb.append("VALUES (ANSWER_SEQ.NEXTVAL, ?, ?, ?, SYSDATE, ?, SYSDATE)");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = connectionMaker.getConnection(); // Connection 획득 메서드 호출
            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setInt(1, answerDTO.getBoardSeq());
            pstmt.setString(2, answerDTO.getContents());
            pstmt.setString(3, answerDTO.getRegId());
            pstmt.setString(4, answerDTO.getModId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 1) {
                log.debug("댓글 추가 완료: {}", answerDTO);
                return 1; // 성공 시 1 반환
            } else {
                log.error("댓글 추가 실패: 데이터베이스에 영향을 미치지 않았습니다.");
                return 0; // 실패 시 0 반환
            }

        } catch (SQLException e) {
            log.error("댓글 추가 중 오류 발생: {}", e.getMessage());
            return 0; // 예외 발생 시 0 반환
        } finally {
            close(conn, pstmt);
        }
    }

    @Override
    public int doUpdate(AnswerDTO answerDTO) {
        // 구현할 필요가 있는 경우 구현
        return 0;
    }

    @Override
    public int doDelete(AnswerDTO answerDTO) {
        // 구현할 필요가 있는 경우 구현
        return 0;
    }

    @Override
    public AnswerDTO doSelectOne(AnswerDTO answerDTO) {
        // 구현할 필요가 있는 경우 구현
        return null;
    }

    @Override
    public int doSaveFile() {
        // 구현할 필요가 있는 경우 구현
        return 0;
    }

    @Override
    public int doReadFile() {
        // 구현할 필요가 있는 경우 구현
        return 0;
    }

    // Connection 닫는 메서드
    private void close(Connection conn, PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.error("PreparedStatement close 오류: {}", e.getMessage());
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("Connection close 오류: {}", e.getMessage());
            }
        }
    }

    // getConnection 메서드는 각각의 환경에 맞게 구현되어야 합니다.
    private Connection getConnection() throws SQLException {
        // Connection 획득 코드
        return null;
    }


	@Override
	public List<AnswerDTO> doRetrieve(DTO search) {
		// TODO Auto-generated method stub
		return null;
	}

}