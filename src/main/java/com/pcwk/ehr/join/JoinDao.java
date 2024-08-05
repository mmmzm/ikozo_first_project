package com.pcwk.ehr.join;

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

//회원가입
public class JoinDao implements WorkDiv<JoinDTO>, PLog{
	private ConnectionMaker connectionMaker;
	
	public JoinDao() {
		connectionMaker=new ConnectionMaker();
	}
	
	@Override
	public List<JoinDTO> doRetrieve(DTO search) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 회원가입
	 * @param param
	 * @return 성공(1)/실패(0)
	 */
	public int doSave(JoinDTO param) {
		int flag=0;
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;//SQL+PARAM
		
		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO lib_user (  \n");
		sb.append("     user_id,            \n");
		sb.append("     user_name,          \n");
		sb.append("     user_tel,           \n");
		sb.append("     user_pw,            \n");
		sb.append("     is_admin,           \n");
		sb.append("     user_email,         \n");
		sb.append("     reg_id,             \n");
		sb.append("     reg_dt,             \n");
		sb.append("     mod_id,             \n");
		sb.append("     mod_dt              \n");
		sb.append(" ) VALUES (              \n");
		sb.append("     ?,                  \n");
		sb.append("     ?,                  \n");
		sb.append("     ?,                  \n");
		sb.append("     ?,                  \n");
		sb.append("     'N',                \n");
		sb.append("     ?,                  \n");
		sb.append("     ?,                  \n");
		sb.append("     SYSDATE,            \n");
		sb.append("     ?,                  \n");
		sb.append("     SYSDATE             \n");
		sb.append(" )                       \n");
		
		log.debug("1. sql:{}",sb.toString());
		log.debug("2.conn:{}",conn);
		log.debug("3.param:{}",param);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt:{}",pstmt);
		
			//param 설정
			pstmt.setString(1, param.getUserId());
			pstmt.setString(2, param.getUserName());
			pstmt.setString(3, param.getUserTel());
			pstmt.setString(4, param.getUserPw());
			pstmt.setString(5, param.getUserEmail());
			pstmt.setString(6, param.getRegId());
			pstmt.setString(7, param.getModId());
			
			//DML
			flag = pstmt.executeUpdate();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
		
			DBUtil.close(conn, pstmt);
			
			log.debug("5.finally conn:{} pstmt:{}",conn,pstmt);
		}
		log.debug("6.flag:{}",flag);
		
		return flag;
	}//--doSave end
	
	/**
	 * 회원아이디 중복 조회
	 * @param param
	 * @return 1(존재)/0
	 */
	public int idCheck(JoinDTO param) {
		int flag = 0;
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;//SQL+PARAM
		ResultSet         rs    = null;//SQL문의 결과
		
		StringBuilder sb = new StringBuilder(500);
		
		sb.append(" select count(*) cnt \n");
		sb.append(" from lib_user       \n");
		sb.append(" where user_id = ?   \n");
	
		log.debug("1. sql:{}",sb.toString());
		log.debug("2.conn:{}",conn);
		log.debug("3.param:{}",param);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt:{}"+pstmt);
			
			//param설정
			pstmt.setString(1, param.getUserId());
			
			//SELECT실행
			rs = pstmt.executeQuery();
			log.debug("5.rs:{}",rs);
			
			if(rs.next()) {
				flag = rs.getInt("cnt");
				log.debug("6.flag:{}",flag);
			}
		}catch(SQLException e) {
			log.debug("-----------------------");
			log.debug("SQLException:"+e.getMessage());
			log.debug("-----------------------");
		}finally {
			DBUtil.close(conn,pstmt,rs);
			log.debug("5.finally conn:{} pstmt:{}",conn,pstmt);
		}
		log.debug("6.outVO:{}",flag);
			
		return flag; 
	}//--idCheck end

	@Override
	public int doUpdate(JoinDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(JoinDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public JoinDTO doSelectOne(JoinDTO param) {
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
