package com.pcwk.ehr.mainpage;

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
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.mypage.LoanListDTO;


public class MainPageDao implements WorkDiv<MainPageDTO>, PLog{
private ConnectionMaker connectionMaker;
	
	public MainPageDao() {
		connectionMaker=new ConnectionMaker();
	}
	
	//메인페이지화면 게시판(공지사항)
	public List<MainPageDTO> doRetrieveAdimY(DTO search) {
		SearchDTO searchVO = (SearchDTO)search;
		
		//검색 조건에 대한 StringBuilder
		StringBuilder sb = new StringBuilder(1000);
		StringBuilder sbWhere = new StringBuilder(200);
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null; // SQL+PARAM
		ResultSet	rs			= null; // SQL문의 결과
		
		List<MainPageDTO> list = new ArrayList<MainPageDTO>();
		
		sb.append(" select title,mod_dt              \n"); 
		sb.append(" from(                            \n");
		sb.append("     select rownum, title, mod_dt \n");
		sb.append("     from board_book              \n");
		sb.append("     where is_admin = 'Y'       \n");
		sb.append("     )                            \n");
		sb.append(" where rownum <=3                 \n");
		
		log.debug("1.sql: {} \n", sb.toString());
		log.debug("2.conn: {} \n", conn);
		log.debug("3.param: {}", search);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);
			
			// SELECT실행
			rs = pstmt.executeQuery();
			log.debug("5.rs:\n" + rs);
			while (rs.next()) {
				// 건수 최대값만 정해짐
				MainPageDTO outVO = new MainPageDTO();
				
				outVO.setTitle(rs.getString("title"));
				outVO.setModDt(rs.getString("mod_dt"));
				
				list.add(outVO);
			} // while
						
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(conn, pstmt, rs); 
			log.debug("5. finally conn : {} pstmt : {} rs : {}",conn, pstmt, rs);
		}// try catch finally
			
			
		return list;
	}//--doRetrieveAdimY end

	
	//메인페이지화면 게시판(소통마당)
	public List<MainPageDTO> doRetrieveAdimN(DTO search) {
		SearchDTO searchVO = (SearchDTO)search;
		
		//검색 조건에 대한 StringBuilder
		StringBuilder sb = new StringBuilder(1000);
		StringBuilder sbWhere = new StringBuilder(200);
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null; // SQL+PARAM
		ResultSet	rs			= null; // SQL문의 결과
		
		List<MainPageDTO> list = new ArrayList<MainPageDTO>();
		
		sb.append(" select title,mod_dt              \n"); 
		sb.append(" from(                            \n");
		sb.append("     select rownum, title, mod_dt \n");
		sb.append("     from board_book              \n");
		sb.append("     where is_admin = 'N'       \n");
		sb.append("     )                            \n");
		sb.append(" where rownum <=3                 \n");
		
		log.debug("1.sql: {} \n", sb.toString());
		log.debug("2.conn: {} \n", conn);
		log.debug("3.param: {}", search);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);
			
			// SELECT실행
			rs = pstmt.executeQuery();
			log.debug("5.rs:\n" + rs);
			while (rs.next()) {
				// 건수 최대값만 정해짐
				MainPageDTO outVO = new MainPageDTO();
				
				outVO.setTitle(rs.getString("title"));
				outVO.setModDt(rs.getString("mod_dt"));
				
				list.add(outVO);
			} // while
						
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(conn, pstmt, rs); 
			log.debug("5. finally conn : {} pstmt : {} rs : {}",conn, pstmt, rs);
		}// try catch finally
			
			
		return list;
	}

	@Override
	public List<MainPageDTO> doRetrieve(DTO search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int doSave(MainPageDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doUpdate(MainPageDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(MainPageDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MainPageDTO doSelectOne(MainPageDTO param) {
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
