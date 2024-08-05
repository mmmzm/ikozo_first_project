package com.pcwk.ehr.genre;

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

public class GenreDAO implements PLog, WorkDiv<GenreDTO> {
	
	private ConnectionMaker connectionMaker;
	
	public GenreDAO() {
		connectionMaker = new ConnectionMaker();
	}
	@Override
	public List<GenreDTO> doRetrieve(DTO search) {
//		1. DriverManager로 데이터 베이스와 연결을 생성
//		2. Connection : 데이터 베이스와 연결 id/pass 인터페이스
//		3. Statement/PreparedStatement : SQL문을 실행 인터페이스
//		4. ResultSet : SQL문의 결과를 저장하고 조회하는 인터페이스
//		5. 연결종료
		SearchDTO searchVO = (SearchDTO) search;
		StringBuilder sbWhere = new StringBuilder();
//		--WHERE book_genre    LIKE : searchWord||'%'     "10"(장르코드)
//		--WHERE genre_name LIKE : searchWord||'%'     "20"   (장르이름)
		
		if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("10")) {
			sbWhere.append("WHERE book_genre LIKE ?||'%' \n");
		}else if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("20")) {
			sbWhere.append("WHERE genre_name LIKE ?||'%' \n");
		}
		
		
		List<GenreDTO> list = new ArrayList<GenreDTO>();
		
		Connection conn = connectionMaker.getConnection(); //2.
		PreparedStatement pstmt = null; //SQL+PARAM 3.
		ResultSet rs = null; //SQL문의 결과 4.
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT tt1.rnum AS num,            \n");
		sb.append("       tt1.book_genre,             \n");
		sb.append("       tt1.genre_name              \n");
		sb.append("    FROM (                         \n");
		sb.append("      SELECT ROWNUM AS rnum,T1.*   \n");
		sb.append("        FROM (                     \n");
		sb.append("         SELECT *                  \n");
		sb.append("         FROM b_genre              \n");
		sb.append(sbWhere.toString());
		sb.append("  )T1                              \n");
		sb.append("  WHERE ROWNUM <= (? * (? -1) + ?) \n");
		sb.append(" )TT1                              \n");
		sb.append("  WHERE rnum >= (? * (? - 1)+1)    \n");
		//sb.append(sbWhere.toString());	
		
		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.search : {}", search);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			//param설정
			//Paging
			//ROWNUM
			if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("10")) {
				
				//장르코드
				//검색어
				pstmt.setString(1, searchVO.getSearchWord());
				
				//ROWNUM
				pstmt.setInt(2, searchVO.getPageSize());
				pstmt.setInt(3, searchVO.getPageNo());
				pstmt.setInt(4, searchVO.getPageSize());
				
				//rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				
				//장르이름
			}else if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("20")) {
			 log.debug("4.1 searchDiv  : {}", searchVO.getSearchDiv());
			 
			    //검색어
			    pstmt.setString(1, searchVO.getSearchWord());
			 
			    //ROWNUM
				pstmt.setInt(2, searchVO.getPageSize());
				pstmt.setInt(3, searchVO.getPageNo());
				pstmt.setInt(4, searchVO.getPageSize());
				
				//rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				
			}else {
				log.debug("4.1 searchDiv  : {}", searchVO.getSearchDiv());
				// ROWNUM
				pstmt.setInt(1, searchVO.getPageSize());
				pstmt.setInt(2, searchVO.getPageNo());
				pstmt.setInt(3, searchVO.getPageSize());

				// rnum
				pstmt.setInt(4, searchVO.getPageSize());
				pstmt.setInt(5, searchVO.getPageNo());
			}
			
			//SELECT 실행
			rs = pstmt.executeQuery();
			log.debug("5.rs : {}", rs);
			while (rs.next()) {
				
				GenreDTO outVO = new GenreDTO();
				
				outVO.setNum(rs.getInt("num"));
				outVO.setBookgenre(rs.getInt("book_genre"));
				outVO.setGenreName(rs.getString("genre_name"));
				
				list.add(outVO);
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt, rs); //자원반납
			log.debug("6.finally conn : {} pstmt : {} ", conn, pstmt, rs);
		}
		
		return list;
	}

	@Override
	public int doSave(GenreDTO param) {
//		1. DriverManager로 데이터 베이스와 연결을 생성
//		2. Connection : 데이터 베이스와 연결 id/pass 인터페이스
//		3. Statement/PreparedStatement : SQL문을 실행 인터페이스
//		4. ResultSet : SQL문의 결과를 저장하고 조회하는 인터페이스
//		5. 연결종료
		int flag = 0;
				
		//1.
	    Connection conn = connectionMaker.getConnection();
	    PreparedStatement pstmt = null;//SQL+PARAM
		
	    StringBuilder sb = new StringBuilder();
	    sb.append("INSERT INTO b_genre ( \n");
	    sb.append("    book_genre,       \n");
	    sb.append("    genre_name        \n");
	    sb.append(") VALUES (            \n");
	    sb.append("    ?,                \n");
	    sb.append("    ?                 \n");
	    sb.append(" )                    \n");
	    
	    log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.param : {}", param);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			 log.debug("4.pstmt : {}", pstmt);
			
			 //param설정
			 pstmt.setInt(1, param.getBookgenre());
			 pstmt.setString(2, param.getGenreName());
			 
			 flag = pstmt.executeUpdate();
			 
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(conn, pstmt); // 자원반납
			
			log.debug("5.finally conn : {} pstmt : {} ", conn, pstmt);
		}
		log.debug("6.flag : {}", flag);
		return flag;
	}

	@Override
	public int doUpdate(GenreDTO param) {
		int flag = 0;
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE b_genre     \n");
		sb.append("SET                \n");
		sb.append("   genre_name = ?  \n");
		sb.append(" WHERE             \n");
		sb.append("   book_genre = ?  \n");
		
		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.param : {}", param);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			pstmt.setString(1, param.getGenreName());
			pstmt.setInt(2, param.getBookgenre());
			
			flag = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
			
			log.debug("5.finally conn : {} pstmt : {} ", conn, pstmt);
		}
		log.debug("6.flag : {}", flag);
		
		return flag;
	}

	@Override
	public int doDelete(GenreDTO param) {
		
		int flag = 0;
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM b_genre    \n");
		sb.append("WHERE                  \n");
		sb.append("        book_genre = ? \n");
		
		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.param : {}", param);
		
		try {
			
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			//param설정
			pstmt.setInt(1, param.getBookgenre());
			
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
	public GenreDTO doSelectOne(GenreDTO param) {
		
		GenreDTO outVO = null;
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT                             \n");
		sb.append("    book_genre,                    \n");
		sb.append("    genre_name                     \n");
		sb.append("FROM                               \n");
		sb.append("    b_genre                        \n");
		sb.append("    WHERE genre_name LIKE ? || '%' \n");
		
		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.param : {}", param);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			pstmt.setString(1, param.getGenreName());
			
			rs = pstmt.executeQuery();
			log.debug("5.rs : {}", rs);
			if (rs.next()) {
				outVO = new GenreDTO();
				
				outVO.setBookgenre(rs.getInt("book_genre"));
				outVO.setGenreName(rs.getString("genre_name"));
				
				log.debug("6.outVO : {}", outVO);
			}
			
			
		}catch (SQLException e) {
			log.debug("────────────────────────────────");
			log.debug("SQLException:" + e.getMessage());
			log.debug("────────────────────────────────");
		}finally {
			DBUtil.close(conn, pstmt, rs);
		}
		
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
