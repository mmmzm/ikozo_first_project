package com.pcwk.ehr.findId;

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


public class FindIdDAO implements WorkDiv<FindIdDTO>, PLog {
		 private ConnectionMaker connectionMaker;
	
		 public FindIdDAO() {
		      connectionMaker = new ConnectionMaker();
		 }
	 
	 
	 /**
	     * 이름과 전화번호를 기반으로 회원의 아이디를 조회하는 메서드
	     * 
	     * @param param FindIdDTO 객체 (userName, userTel 필수)
	     * @return String 회원 아이디
	     */
	    public String findUserId(FindIdDTO param) {
	        String userId = null;
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;

	        try {
	            conn = connectionMaker.getConnection();
	            StringBuilder sb = new StringBuilder();
	            sb.append("SELECT USER_ID ");
	            sb.append("FROM LIB_USER ");
	            sb.append("WHERE USER_NAME = ? ");
	            sb.append("AND USER_TEL = ? ");

	            pstmt = conn.prepareStatement(sb.toString());
	            pstmt.setString(1, param.getUserName());
	            pstmt.setString(2, param.getUserTel());

	            rs = pstmt.executeQuery();

	            if (rs.next()) {
	                userId = rs.getString("user_id");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); // 예외 처리
	        } finally {
	            DBUtil.close(conn, pstmt, rs); // 리소스 반환
	        }

	        return userId;
	    }


	@Override
	public List<FindIdDTO> doRetrieve(DTO search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int doSave(FindIdDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doUpdate(FindIdDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(FindIdDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FindIdDTO doSelectOne(FindIdDTO param) {
		// TODO Auto-generated method stub
		return null;
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
