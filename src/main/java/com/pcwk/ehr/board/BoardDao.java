package com.pcwk.ehr.board;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import java.util.Date;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pcwk.ehr.cmn.ConnectionMaker;
import com.pcwk.ehr.cmn.DBUtil;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.cmn.SearchDTO;

public class BoardDao implements WorkDiv<BoardDTO>, PLog {

    private ConnectionMaker connectionMaker;

    public BoardDao() {
        connectionMaker = new ConnectionMaker();
    }
    
    public List<BoardDTO> getPagedBoardList(int pageNumber, int pageSize) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BoardDTO> boardList = new ArrayList<>();

        try {
            conn = connectionMaker.getConnection(); // Connection 생성
            String sql = "SELECT * FROM (SELECT ROWNUM AS rnum, b.* FROM (SELECT * FROM BOARD_BOOK ORDER BY REG_DT DESC) b WHERE ROWNUM <= ?) WHERE rnum >= ?";
            pstmt = conn.prepareStatement(sql);

            // 페이지에 해당하는 시작 번호와 끝 번호 계산
            int startRow = (pageNumber - 1) * pageSize + 1;
            int endRow = pageNumber * pageSize;

            pstmt.setInt(1, endRow);
            pstmt.setInt(2, startRow);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                BoardDTO board = new BoardDTO();
                board.setSeq(rs.getInt("SEQ"));
                board.setTitle(rs.getString("TITLE"));
                board.setContents(rs.getString("CONTENTS"));
                board.setRegId(rs.getString("REG_ID"));
                board.setRegDt(rs.getDate("REG_DT"));
                board.setReadCnt(rs.getInt("READ_CNT"));

                boardList.add(board);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return boardList;
    }
    
    //조회시작
    @Override
    public List<BoardDTO> doRetrieve(DTO search) {
        SearchDTO searchVO = (SearchDTO) search;

        StringBuilder sb = new StringBuilder(1000);
        StringBuilder sbWhere = new StringBuilder(1000);

        if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("10")) {
            sbWhere.append("WHERE title LIKE ?||'%' \n");
        } else if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("20")) {
            sbWhere.append("WHERE contents LIKE ?||'%' \n");
        }

        Connection conn = connectionMaker.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<BoardDTO> list = new ArrayList<BoardDTO>();

        sb.append("SELECT A.*, B.* \n");
        sb.append("FROM( \n");
        sb.append("    SELECT TT1.rnum AS num, \n");
        sb.append("           TT1.seq, \n");
        sb.append("           TT1.title, \n");
        sb.append("           TT1.read_cnt, \n");
        sb.append("           TT1.contents, \n");
        sb.append("           TT1.reg_id, \n");
        sb.append("           DECODE(TO_CHAR(TT1.reg_dt, 'YYYY/MM/DD') \n");
        sb.append("           , TO_CHAR(SYSDATE, 'YYYY/MM/DD') \n");
        sb.append("           , TO_CHAR(tt1.reg_dt, 'HH24:MI') \n");
        sb.append("           , TO_CHAR(tt1.reg_dt, 'YYYY/MM/DD') \n");
        sb.append("           ) AS reg_dt, \n");
        sb.append("           TT1.mod_id, \n");
        sb.append("           DECODE(TO_CHAR(TT1.mod_dt, 'YYYY/MM/DD') \n");
        sb.append("           , TO_CHAR(SYSDATE, 'YYYY/MM/DD') \n");
        sb.append("           , TO_CHAR(tt1.mod_dt, 'HH24:MI') \n");
        sb.append("           , TO_CHAR(tt1.mod_dt, 'YYYY/MM/DD') \n");
        sb.append("           ) AS mod_dt, \n");
        sb.append("           TT1.is_admin \n");
        sb.append("    FROM( \n");
        sb.append("        SELECT ROWNUM AS rnum, T1.* \n");
        sb.append("        FROM( \n");
        sb.append("            SELECT * \n");
        sb.append("            FROM BOARD_BOOK \n");
        sb.append(sbWhere.toString());
        sb.append("            ORDER BY reg_dt DESC \n");
        sb.append("        )T1 \n");
        sb.append("        WHERE ROWNUM <= ( ? * (? - 1) + ?) \n");
        sb.append("    )TT1 \n");
        sb.append("    WHERE rnum >= (? * (? -1) + 1) \n");
        sb.append(")A,( \n");
        sb.append("    SELECT COUNT(*) totalCnt \n");
        sb.append("    FROM BOARD_BOOK \n");
        sb.append(sbWhere.toString());
        sb.append(")B");

        log.debug("1.sql: {} \n", sb.toString());
        log.debug("2.conn: {} \n", conn);
        log.debug("3.param: {}", search);

        try {
            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4. pstmt : {}", pstmt);

            if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("10")) {
                pstmt.setString(1, searchVO.getSearchWord());
                pstmt.setInt(2, searchVO.getPageSize());
                pstmt.setInt(3, searchVO.getPageNo());
                pstmt.setInt(4, searchVO.getPageSize());
                pstmt.setInt(5, searchVO.getPageSize());
                pstmt.setInt(6, searchVO.getPageNo());
                pstmt.setString(7, searchVO.getSearchWord());
            } else if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("20")) {
                pstmt.setString(1, searchVO.getSearchWord());
                pstmt.setInt(2, searchVO.getPageSize());
                pstmt.setInt(3, searchVO.getPageNo());
                pstmt.setInt(4, searchVO.getPageSize());
                pstmt.setInt(5, searchVO.getPageSize());
                pstmt.setInt(6, searchVO.getPageNo());
                pstmt.setString(7, searchVO.getSearchWord());
            } else {
                pstmt.setInt(1, searchVO.getPageSize());
                pstmt.setInt(2, searchVO.getPageNo());
                pstmt.setInt(3, searchVO.getPageSize());
                pstmt.setInt(4, searchVO.getPageSize());
                pstmt.setInt(5, searchVO.getPageNo());
            }

            rs = pstmt.executeQuery();
            log.debug("5.rs:\n" + rs);
            while (rs.next()) {
                BoardDTO outVO = new BoardDTO();

                outVO.setSeq(rs.getInt("seq"));
                outVO.setTitle(rs.getString("title"));
                outVO.setReadCnt(rs.getInt("read_cnt"));
                outVO.setContents(rs.getString("contents"));
                outVO.setRegId(rs.getString("reg_id"));
                outVO.setRegDt(rs.getDate("reg_dt"));
                outVO.setModId(rs.getString("mod_id"));
                outVO.setModDt(rs.getDate("mod_dt"));
                outVO.setIsAdmin(rs.getString("is_admin"));
                outVO.setTotalCnt(rs.getInt("totalCnt"));

                list.add(outVO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
            log.debug("5. finally conn : {} pstmt : {} rs : {}", conn, pstmt, rs);
        }

        return list;
    }
    
    //등록
    @Override
    public int doSave(BoardDTO param) {
        int flag = 0;
        Connection conn = connectionMaker.getConnection();
        PreparedStatement pstmt = null;

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO BOARD_BOOK ( \n");
        sb.append("    seq,                 \n");
        sb.append("    title,               \n");
        sb.append("    read_cnt,            \n");
        sb.append("    contents,            \n");
        sb.append("    reg_id,              \n");
        sb.append("    reg_dt,              \n");
        sb.append("    mod_id,              \n");
        sb.append("    mod_dt,              \n");
        sb.append("    is_admin             \n");
        sb.append(") VALUES (               \n");
        sb.append("    board_seq.NEXTVAL,   \n");
        sb.append("    ?,                   \n");
        sb.append("    0,                   \n");
        sb.append("    ?,                   \n");
        sb.append("    ?,                   \n");
        sb.append("    SYSDATE,             \n");
        sb.append("    ?,                   \n");
        sb.append("    SYSDATE,             \n");
        sb.append("    ?                    \n");
        sb.append(")");

        log.debug("1.sql: {}", sb.toString());
        log.debug("2.conn: {}", conn);
        log.debug("3.param: {}", param);

        try {
            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4. pstmt : {}", pstmt);

            pstmt.setString(1, param.getTitle());
            pstmt.setString(2, param.getContents());
            pstmt.setString(3, param.getRegId());
            pstmt.setString(4, param.getModId());
            pstmt.setString(5, param.getIsAdmin());

            flag = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
            log.debug("5. finally conn : {} pstmt : {}", conn, pstmt);
        }

        log.debug("6. flag: {}", flag);
        return flag;
    }

    @Override
    public int doDelete(BoardDTO param) {
        int flag = 0;
        Connection conn = connectionMaker.getConnection();
        PreparedStatement pstmt = null;

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM BOARD_BOOK \n");
        sb.append("WHERE seq = ?");

        log.debug("1.sql: {}", sb.toString());
        log.debug("2.conn: {}", conn);
        log.debug("3.param: {}", param);

        try {
            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4. pstmt : {}", pstmt);

            pstmt.setInt(1, param.getSeq());

            flag = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
            log.debug("5. finally conn : {} pstmt : {}", conn, pstmt);
        }

        log.debug("6. flag: {}", flag);
        return flag;
    }

    @Override
    public int doUpdate(BoardDTO param) {
        int flag = 0;
        Connection conn = connectionMaker.getConnection();
        PreparedStatement pstmt = null;

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE BOARD_BOOK             \n");
        sb.append("SET title = ?,           \n");
        sb.append("    contents = ?,        \n");
        sb.append("    mod_id = ?,          \n");
        sb.append("    mod_dt = SYSDATE     \n");
        sb.append("WHERE seq = ?              ");

        log.debug("1.sql: {}", sb.toString());
        log.debug("2.conn: {}", conn);
        log.debug("3.param: {}", param);

        try {
            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4. pstmt : {}", pstmt);

            pstmt.setString(1, param.getTitle());
            pstmt.setString(2, param.getContents());
            pstmt.setString(3, param.getModId());
            pstmt.setInt(4, param.getSeq());

            flag = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
            log.debug("5. finally conn : {} pstmt : {}", conn, pstmt);
        }

        log.debug("6. flag: {}", flag);
        return flag;
    }

    @Override
    public BoardDTO doSelectOne(BoardDTO param) {
        BoardDTO outVO = null;
        Connection conn = connectionMaker.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * \n");
        sb.append("FROM BOARD_BOOK \n");
        sb.append("WHERE seq = ?");

        log.debug("1.sql: {}", sb.toString());
        log.debug("2.conn: {}", conn);
        log.debug("3.param: {}", param);

        try {
            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4. pstmt : {}", pstmt);

            pstmt.setInt(1, param.getSeq());

            rs = pstmt.executeQuery();
            log.debug("5.rs:\n" + rs);
            if (rs.next()) {
                outVO = new BoardDTO();

                outVO.setSeq(rs.getInt("seq"));
                outVO.setTitle(rs.getString("title"));
                outVO.setReadCnt(rs.getInt("read_cnt"));
                outVO.setContents(rs.getString("contents"));
                outVO.setRegId(rs.getString("reg_id"));
                outVO.setRegDt(rs.getDate("reg_dt"));
                outVO.setModId(rs.getString("mod_id"));
                outVO.setModDt(rs.getDate("mod_dt"));
                outVO.setIsAdmin(rs.getString("is_admin"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
            log.debug("5. finally conn : {} pstmt : {} rs : {}", conn, pstmt, rs);
        }

        return outVO;
    }
    
    //조회수 증가
    public int doUpdateReadcnt(BoardDTO param) {
        int flag = 0;
        Connection conn = connectionMaker.getConnection();
        
        PreparedStatement pstmt = null; // SQL + PARAM        
        StringBuilder sb = new StringBuilder(200);
        // 조회수 증가 메서드 SQL 명령문
        // read_cnt를 처음에 0으로 넣어야지 null이라고 해놨으니 null + 1 해서 null로 나오는거임 
        sb.append("UPDATE BOARD_BOOK                        \n");
        sb.append("SET    read_cnt = NVL(read_cnt, 0) + 1   \n"); 
        sb.append("WHERE  seq = ?                           \n");

        log.debug("1. sql : {}", sb.toString());
        log.debug("2. conn : {}", conn);
        log.debug("3. param : {}", param);
        try {
            // conn.setAutoCommit(false); // 자동 커밋 정지
            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4. pstmt : {}", pstmt);
            
            pstmt.setInt(1, param.getSeq());
            
            // DML 수행
            flag = pstmt.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            DBUtil.close(conn, pstmt);
            
            log.debug("5. finally conn : {} pstmt : {}", conn, pstmt);
        }// try catch
        log.debug("6. flag: {}", flag);
        
        return flag;
    }
    
    //최초에 board01.jsp 화면에서 새로고침하면 여기로 옴
    public List<BoardDTO> getAdminBoardList(String isAdmin) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BoardDTO> boardList = new ArrayList<>();

        try {
            conn = connectionMaker.getConnection();

            String sql = "SELECT seq, title, contents, reg_id, reg_dt, read_cnt, is_admin " +
                         "FROM BOARD_BOOK " +
                         "WHERE is_admin = ? " +
                         "ORDER BY reg_dt DESC";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isAdmin.toUpperCase()); // 대문자 변환

            rs = pstmt.executeQuery();

            while (rs.next()) {
                BoardDTO board = new BoardDTO();
                board.setSeq(rs.getInt("seq"));
                board.setTitle(rs.getString("title"));
                board.setContents(rs.getString("contents"));
                board.setRegId(rs.getString("reg_id"));
                board.setRegDt(rs.getDate("reg_dt"));
                board.setReadCnt(rs.getInt("read_cnt"));
                board.setIsAdmin(rs.getString("is_admin"));

                boardList.add(board);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return boardList;
    }


    //BoardDetail
    public BoardDTO getBoardDetail(int seq) {
        BoardDTO board = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.getConnection();
            String sql = "SELECT seq, title, contents, reg_id, reg_dt, mod_id, mod_dt, read_cnt FROM BOARD_BOOK WHERE seq=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, seq);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                board = new BoardDTO();
                board.setSeq(rs.getInt("seq"));
                board.setTitle(rs.getString("title"));
                board.setContents(rs.getString("contents"));
                board.setRegId(rs.getString("reg_id"));
                board.setRegDt(rs.getDate("reg_dt"));
                board.setModId(rs.getString("mod_id"));
                board.setModDt(rs.getDate("mod_dt"));
                board.setReadCnt(rs.getInt("read_cnt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pstmt, conn);
        }

        return board;
    }
    public BoardDTO viewBoardDetail(int seq, HttpSession session) {
        BoardDTO board = getBoardDetail(seq); // 기존의 게시글 상세 정보 조회

        if (board != null) {
            // 세션에 게시글 정보를 저장
            session.setAttribute("board", board);
        } else {
            // 예외 처리 또는 오류 메시지 설정
            // 예: session.setAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
        }

        return board;
    }
    
 

    private void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	@Override
	public int doSaveFile() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doReadFile() {
		// TODO Auto-generated method stub
		return 0;
	}

}