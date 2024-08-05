package com.pcwk.ehr.resetpw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.pcwk.ehr.cmn.ConnectionMaker;
import com.pcwk.ehr.cmn.DBUtil;
import com.pcwk.ehr.cmn.PLog;
import java.util.UUID;

public class ResetPwDAO implements PLog {
    private ConnectionMaker connectionMaker;
	public String newPassword;

    public ResetPwDAO() {
        connectionMaker = new ConnectionMaker();
    }

    public boolean resetPassword(ResetPwDTO param) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isResetSuccessful = false;

        try {
            conn = connectionMaker.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE lib_user ");
            sb.append("SET user_pw = ? ");
            sb.append("WHERE user_id = ? ");
            sb.append("AND user_name = ? ");
            sb.append("AND user_tel = ? ");

            pstmt = conn.prepareStatement(sb.toString());

            //random password
            String newPassword = UUID.randomUUID().toString().substring(0, 8);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, param.getUserId());
            pstmt.setString(3, param.getUserName());
            pstmt.setString(4, param.getUserTel());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isResetSuccessful = true;
                param.setNewPassword(newPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, null);
        }

        return isResetSuccessful;
    }
}
