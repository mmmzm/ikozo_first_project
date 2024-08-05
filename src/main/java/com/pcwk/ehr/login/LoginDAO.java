package com.pcwk.ehr.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.ConnectionMaker;
import com.pcwk.ehr.cmn.DBUtil;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.WorkDiv;

public class LoginDAO implements WorkDiv<LoginDTO>, PLog {
    private ConnectionMaker connectionMaker;

    public LoginDAO() {
        connectionMaker = new ConnectionMaker();
    }

    public int checkLogin(LoginDTO param) {
        int flag = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.getConnection();
            StringBuilder sb = new StringBuilder(500);
            sb.append(" SELECT COUNT(*) cnt  \n");
            sb.append("   FROM LIB_USER      \n");
            sb.append("  WHERE USER_ID = ?   \n");
            sb.append("    AND USER_PW = ?   \n");

            log.debug("1.sql: {} \n", sb.toString());
            log.debug("2.conn: {}", conn);
            log.debug("3.param: {} ", param);

            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4.pstmt: {} ", pstmt);

            pstmt.setString(1, param.getUserId());
            pstmt.setString(2, param.getUserPw());

            rs = pstmt.executeQuery();
            log.debug("5.rs:{}", rs);

            if (rs.next()) {
                flag = rs.getInt("cnt");
                log.debug("6.flag: {}", flag);
            }
        } catch (SQLException e) {
            log.debug("────────────────────────");
            log.debug("SQLException:" + e.getMessage());
            log.debug("────────────────────────");
        } finally {
            DBUtil.close(conn, pstmt, rs); // 리소스 반환 추가
        }
        return flag;
    }

    public int checkUsedId(LoginDTO param) {
        int flag = 0;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.getConnection();
            StringBuilder sb = new StringBuilder(500);
            sb.append(" SELECT COUNT(*) cnt  \n");
            sb.append("   FROM member        \n");
            sb.append("  WHERE member_id = ? \n");

            log.debug("1.sql: {} \n", sb.toString());
            log.debug("2.conn: {}", conn);
            log.debug("3.param: {} ", param);

            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4.pstmt: {} ", pstmt);

            pstmt.setString(1, param.getUserId());

            rs = pstmt.executeQuery();
            log.debug("5.rs:{}", rs);

            if (rs.next()) {
                flag = rs.getInt("cnt");
                log.debug("6.flag: {}", flag);
            }

        } catch (SQLException e) {
            log.debug("────────────────────────");
            log.debug("SQLException:" + e.getMessage());
            log.debug("────────────────────────");
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return flag;
    }

    public int checkUsedPw(LoginDTO param) {
        int flag = 0;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.getConnection();
            StringBuilder sb = new StringBuilder(500);
            sb.append(" SELECT COUNT(*) cnt  \n");
            sb.append("   FROM LIB_USER        \n");
            sb.append("  WHERE USER_ID = ? \n");
            sb.append("    AND USER_PW  = ? \n");

            log.debug("1.sql: {} \n", sb.toString());
            log.debug("2.conn: {}", conn);
            log.debug("3.param: {} ", param);

            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4.pstmt: {} ", pstmt);

            pstmt.setString(1, param.getUserId());
            pstmt.setString(2, param.getUserPw());

            rs = pstmt.executeQuery();
            log.debug("5.rs:{}", rs);

            if (rs.next()) {
                flag = rs.getInt("cnt");
                log.debug("6.flag: {}", flag);
            }

        } catch (SQLException e) {
            log.debug("────────────────────────");
            log.debug("SQLException:" + e.getMessage());
            log.debug("────────────────────────");
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return flag;
    }

    @Override
    public LoginDTO doSelectOne(LoginDTO param) {
        LoginDTO outVO = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.getConnection();
            StringBuilder sb = new StringBuilder(500);
            sb.append(" SELECT USER_ID, USER_NAME, USER_TEL, USER_PW, IS_ADMIN \n");
            sb.append("   FROM LIB_USER t1 \n");
            sb.append("  WHERE USER_ID = ? \n");

            log.debug("1.sql: {} \n", sb.toString());
            log.debug("2.conn: {}", conn);
            log.debug("3.param: {} ", param);

            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4.pstmt: {} ", pstmt);
            pstmt.setString(1, param.getUserId());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                outVO = new LoginDTO();
                outVO.setUserId(rs.getString("USER_ID"));
                outVO.setUserName(rs.getString("USER_NAME"));
                outVO.setUserTel(rs.getString("USER_TEL"));
                outVO.setUserPw(rs.getString("USER_PW"));
                outVO.setIsAdmin(rs.getString("IS_ADMIN"));

                log.debug("5.outVO: {} ", outVO);
            }
        } catch (SQLException e) {
            log.debug("────────────────────────");
            log.debug("SQLException:" + e.getMessage());
            log.debug("────────────────────────");
        } finally {
            DBUtil.close(conn, pstmt, rs); // 리소스 반환 추가
        }
        return outVO;
    }

    public DTO doMemberSelect(LoginDTO param) {
        LoginDTO outVO = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.getConnection();
            StringBuilder sb = new StringBuilder(500);
            sb.append(" SELECT member_id, member_name, member_tel, password, is_admin \n");
            sb.append("   FROM member \n");
            sb.append("  WHERE member_id = ? \n");

            log.debug("1.sql: {} \n", sb.toString());
            log.debug("2.conn: {}", conn);
            log.debug("3.param: {} ", param);

            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4.pstmt: {} ", pstmt);
            pstmt.setString(1, param.getUserId());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                outVO = new LoginDTO();
                outVO.setUserId(rs.getString("member_id"));
                outVO.setUserName(rs.getString("member_name"));
                outVO.setUserTel(rs.getString("member_tel"));
                outVO.setUserPw(rs.getString("password"));
                outVO.setIsAdmin(rs.getString("is_admin"));

                log.debug("5.outVO: {} ", outVO);
            }
        } catch (SQLException e) {
            log.debug("────────────────────────");
            log.debug("SQLException:" + e.getMessage());
            log.debug("────────────────────────");
        } finally {
            DBUtil.close(conn, pstmt, rs); // 리소스 반환 추가
        }
        return outVO;
    }

    
   
    @Override
    public List<LoginDTO> doRetrieve(DTO search) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int doDelete(LoginDTO param) {
        // TODO Auto-generated method stub
        return 0;
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

    @Override
    public int doSave(LoginDTO param) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int doUpdate(LoginDTO param) {
        // TODO Auto-generated method stub
        return 0;
    }
}
