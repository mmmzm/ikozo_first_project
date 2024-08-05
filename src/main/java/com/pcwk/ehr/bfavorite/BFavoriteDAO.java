package com.pcwk.ehr.bfavorite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.ConnectionMaker;
import com.pcwk.ehr.cmn.DBUtil;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.cmn.WorkDiv;

public class BFavoriteDAO implements PLog, WorkDiv<BFavoriteDTO> {

	private ConnectionMaker connectionMaker;
	
	public BFavoriteDAO() {
		connectionMaker = new ConnectionMaker();
	}
	
	@Override
	public List<BFavoriteDTO> doRetrieve(DTO search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int doSave(BFavoriteDTO param) {
		
		int flag = 0;
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO favorite (  \n");
		sb.append("    fav_seq,            \n");
		sb.append("    user_id,            \n");
		sb.append("    book_code,          \n");
		sb.append("    reg_dt              \n");
		sb.append(") VALUES (              \n");
		sb.append("    f_num_seq.NEXTVAL,  \n");
		sb.append("    ?,                  \n");
		sb.append("    ?,                  \n");
		sb.append("    SYSDATE             \n");
		sb.append(")                       \n");
		
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
	public int doUpdate(BFavoriteDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(BFavoriteDTO param) {
		log.debug("doDelete()");
		
		int flag = 0;
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM favorite  \n");
		sb.append("WHERE                 \n");
		sb.append("   user_id = ?        \n");
		
		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.param : {}", param);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			pstmt.setString(1, param.getUserId());
			
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
	public BFavoriteDTO doSelectOne(BFavoriteDTO param) {
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
