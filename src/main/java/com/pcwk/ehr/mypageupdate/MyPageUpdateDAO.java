package com.pcwk.ehr.mypageupdate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.pcwk.ehr.cmn.ConnectionMaker;
import com.pcwk.ehr.cmn.DBUtil;
import com.pcwk.ehr.cmn.PLog;

public class MyPageUpdateDAO implements PLog {
    private ConnectionMaker connectionMaker;

    public MyPageUpdateDAO() {
        connectionMaker = new ConnectionMaker();
    }

    /**
     * 사용자 비밀번호를 업데이트하는 메서드
     * 
     * @param userId 사용자 아이디
     * @param password 수정할 비밀번호
     * @return boolean 업데이트 성공 여부 (true: 성공, false: 실패)
     */
    public boolean updatePassword(String userId, String password) {
        boolean isSuccess = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = connectionMaker.getConnection();
            String sql = "UPDATE LIB_USER SET USER_PW = ? WHERE USER_ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setString(2, userId);

            int rowCount = pstmt.executeUpdate();
            if (rowCount > 0) {
                isSuccess = true;
            }
        } catch (SQLException e) {
            log.debug("SQLException: " + e.getMessage());
        } finally {
            DBUtil.close(conn, pstmt);
        }

        return isSuccess;
    }
}
