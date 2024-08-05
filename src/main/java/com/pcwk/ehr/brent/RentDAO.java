package com.pcwk.ehr.brent;

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

public class RentDAO implements PLog, WorkDiv<RentDTO> {

	private ConnectionMaker connectionMaker;
	
	public RentDAO() {
		connectionMaker = new ConnectionMaker();
	}
	
	@Override
	public List<RentDTO> doRetrieve(DTO search) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int rentCheck(RentDTO param) {
		int flag = 0;
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *             \n");
		sb.append(" FROM RENT           \n");
		sb.append(" WHERE book_code = ? \n");
		sb.append(" AND returned_date is null \n");
		
		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.param : {}", param);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			pstmt.setInt(1, param.getBookCode());
			
			rs = pstmt.executeQuery();
			log.debug("5.rs : {}", rs);
			
			if(rs.next()) {
				flag = 1;
				log.debug("6.flag : {}", flag);
			}
			
			
		}catch(SQLException e) {
			log.debug("────────────────────────────────");
			log.debug("SQLException:" + e.getMessage());
			log.debug("────────────────────────────────");
		}finally {
			DBUtil.close(conn, pstmt);
		}
		return flag;
	}
	

	@Override
	public int doSave(RentDTO param) {
//		1. DriverManager로 데이터 베이스와 연결을 생성
//		2. Connection : 데이터 베이스와 연결 id/pass 인터페이스
//		3. Statement/PreparedStatement : SQL문을 실행 인터페이스
//		4. ResultSet : SQL문의 결과를 저장하고 조회하는 인터페이스
//		5. 연결종료
		int flag = 0;
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO rent (       \n");
		sb.append("    rent_code,           \n");
		sb.append("    user_id,             \n");
		sb.append("    book_code,           \n");
		sb.append("    rent_date,           \n");
		sb.append("    due_date             \n");
		sb.append(") VALUES (               \n");
		sb.append("    r_num_seq.NEXTVAL,   \n");
		sb.append("    ?,                   \n");
		sb.append("    ?,                   \n");
		sb.append("    SYSDATE,             \n");
		sb.append("    SYSDATE +7           \n");
		sb.append(")                        \n");
		
		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.param : {}", param);
		
		try {
			conn.setAutoCommit(true);
			
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			pstmt.setString(1, param.getUserId());
			pstmt.setInt(2, param.getBookCode());

			flag = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(conn, pstmt);
			log.debug("5.finally conn : {} pstmt : {} ", conn, pstmt);
		}
		log.debug("6.flag : {}", flag);
		return flag;
	}

	@Override
	public int doUpdate(RentDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(RentDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public RentDTO doSelectOne(RentDTO param) {
		RentDTO outVO = null;
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT                   \n");
		sb.append("    rent_code,           \n");
		sb.append("    user_id,             \n");
		sb.append("    book_code,           \n");
		sb.append("    rent_date,           \n");
		sb.append("    due_date,            \n");
		sb.append("    returned_date,       \n");
		sb.append("    noreturn_count,      \n");
		sb.append("    extra_sum,           \n");
		sb.append("    reg_dt,              \n");
		sb.append("    reg_id,              \n");
		sb.append("    mod_id,              \n");
		sb.append("    mod_dt               \n");
		sb.append("FROM                     \n");
		sb.append("    rent                 \n");
		sb.append("    WHERE rent_code = ?  \n");
		
		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.param : {}", param);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			pstmt.setInt(1, param.getBookCode());
			
			rs = pstmt.executeQuery();
			log.debug("5.rs : {}", rs);
			if(rs.next()) {
				outVO = new RentDTO();
				
				outVO.setBookCode(rs.getInt("rent_code"));
			
				log.debug("5.outVO : {}", outVO);
			}
			
		}catch(SQLException e) {
			log.debug("────────────────────────────────");
			log.debug("SQLException:" + e.getMessage());
			log.debug("────────────────────────────────");
		}
			DBUtil.close(conn, pstmt, rs);
		return outVO;
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
