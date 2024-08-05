package com.pcwk.ehr.booklist;

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

public class BookDAO implements PLog, WorkDiv<BookDTO> {
	
	private ConnectionMaker connectionMaker;

	public BookDAO() {
		connectionMaker = new ConnectionMaker();
	}
	
	
	@Override
	public List<BookDTO> doRetrieve(DTO search) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<BookDTO> doRetrieve2(DTO search) {
//		--WHERE title    LIKE : searchWord||'%'     "10" (제목)
//		--WHERE contents LIKE : searchWord||'%'     "20" (출판사)
//		--WHERE mod_id      = : searchWord||'%'     "30" (작가)
//		--WHERE title    LIKE : searchWord||'%'     "40" (장르)
//		   --OR contents LIKE : searchWord||'%'   
//		--WHERE seq         = : searchWord||'%'     "50" (시퀀스)
		
		SearchDTO searchVO = (SearchDTO)search;
		StringBuilder sbWhere = new StringBuilder();
		StringBuilder sbWhere2 = new StringBuilder();
		StringBuilder sbWhere3 = new StringBuilder();
		if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("10")) {
			sbWhere.append("WHERE book_name LIKE ?||'%' \n");
		}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("20")) {
			sbWhere.append("WHERE publisher LIKE ?||'%' \n");
		}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("30")) {
			sbWhere.append("WHERE author LIKE ? || '%' \n");
		}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("40")) {
			sbWhere3.append("WHERE b.genre_name LIKE ? || '%' \n");
			sbWhere2.append(" aa                                             \n");
			sbWhere2.append(" JOIN b_genre bb ON aa.book_genre=bb.book_genre \n");
			sbWhere2.append(" WHERE bb.genre_name LIKE ? || '%'                \n");
		}
		
		List<BookDTO> list = new ArrayList<BookDTO>();
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT A.*,B.*                                                    \n");
		sb.append("  FROM (                                                          \n");
		sb.append("	SELECT tt1.rnum AS num,                                          \n");
		sb.append("		   tt1.book_code, tt1.book_name,                             \n");   
		sb.append("		   tt1.genre_name,                                           \n");
		sb.append("		   TO_CHAR(tt1.book_pub_date,'YYYY/MM/DD') AS book_pub_date, \n");
		sb.append("		   tt1.publisher,                                            \n");
		sb.append("		   tt1.author,                                               \n");
		sb.append("		   tt1.book_info                                             \n");
		sb.append("	  FROM (                                                         \n");
		sb.append("		SELECT ROWNUM AS rnum, T1.*                                  \n");
		sb.append("		  FROM (                                                     \n");
		sb.append("			SELECT a.book_code ,a.book_name,                         \n");
		sb.append("				   b.genre_name,                                     \n");
		sb.append("				   a.book_pub_date,                                  \n");
		sb.append("				   a.publisher,                                      \n");
		sb.append("				   a.author,                                         \n");
		sb.append("				   a.book_info                                       \n");
		sb.append("			  FROM book a                                            \n");
		sb.append("			  JOIN b_genre b ON a.book_genre = b.book_genre          \n");
		sb.append("			 -- WHERE 조건문                                          \n");
		sb.append(sbWhere.toString());                                 
		sb.append(sbWhere3.toString());  
		sb.append("		  ) T1                                                       \n");
		sb.append("		 --WHERE ROWNUM <= 10                                        \n");
		sb.append("		 WHERE ROWNUM <= (? * (? -1) + ?)                            \n");
		sb.append("	  ) TT1                                                          \n");
		sb.append("	 --WHERE rnum >= 1                                               \n");
		sb.append("	 WHERE rnum >= (? * (? -1) +1 )                                  \n");
		sb.append("	)A,(                                                             \n");
		sb.append(" SELECT COUNT(*) totalCnt                                         \n");
		sb.append("  FROM book                                                       \n");
		sb.append("  --WHERE 조건                                                     \n");
		sb.append(sbWhere.toString());
		sb.append(sbWhere2.toString());  
		sb.append("  )B                                                              \n");

		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.search : {}", search);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			//ROWNUM
			if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("10")) {
				
				pstmt.setString(1, searchVO.getSearchWord());
				
				//ROWNUM
				pstmt.setInt(2, searchVO.getPageSize());
				pstmt.setInt(3,searchVO.getPageNo());
				pstmt.setInt(4, searchVO.getPageSize());
				
				//rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				
				pstmt.setString(7, searchVO.getSearchWord());
				
			} else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("20")) {
				log.debug("4.1 searchDiv  : {}", searchVO.getSearchDiv());
				
				pstmt.setString(1, searchVO.getSearchWord());
				
				//ROWNUM
				pstmt.setInt(2, searchVO.getPageSize());
				pstmt.setInt(3,searchVO.getPageNo());
				pstmt.setInt(4, searchVO.getPageSize());
				
				//rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				
				pstmt.setString(7, searchVO.getSearchWord());
				
			} else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("30")) {
				
				pstmt.setString(1, searchVO.getSearchWord());
				
				//ROWNUM
				pstmt.setInt(2, searchVO.getPageSize());
				pstmt.setInt(3,searchVO.getPageNo());
				pstmt.setInt(4, searchVO.getPageSize());
				
				//rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				
				pstmt.setString(7, searchVO.getSearchWord());
				
			} else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("40")) {
				
                pstmt.setString(1, searchVO.getSearchWord());
				
				//ROWNUM
				pstmt.setInt(2, searchVO.getPageSize());
				pstmt.setInt(3,searchVO.getPageNo());
				pstmt.setInt(4, searchVO.getPageSize());
				
				//rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				
				pstmt.setString(7, searchVO.getSearchWord());
				
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
				BookDTO outVO = new BookDTO();
				outVO.setBookCode(rs.getInt("book_code"));
				outVO.setBookName(rs.getString("book_name"));
				outVO.setBookPubDate(rs.getString("book_pub_date"));
				outVO.setPublisher(rs.getString("publisher"));
				outVO.setAuthor(rs.getString("author"));
				outVO.setBookInfo(rs.getString("book_info"));
				outVO.setGenreName(rs.getString("genre_name"));
				//outVO.setBookGenre(rs.getInt("book_genre"));
				
				outVO.setTotalCnt(rs.getInt("totalCnt"));
				
				list.add(outVO);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
		  DBUtil.close(conn, pstmt, rs); // 자원반납
		  log.debug("6.finally conn : {} pstmt : {} ", conn, pstmt, rs);
		}
		return list;
	}

	@Override
	public int doSave(BookDTO param) {
//		1. DriverManager로 데이터 베이스와 연결을 생성
//		2. Connection : 데이터 베이스와 연결 id/pass 인터페이스
//		3. Statement/PreparedStatement : SQL문을 실행 인터페이스
//		4. ResultSet : SQL문의 결과를 저장하고 조회하는 인터페이스
//		5. 연결종료
		
		int flag = 0;
		
		//1.
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;// SQL+PARAM
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO book (      \n");
		sb.append("    book_code,         \n");
		sb.append("    book_genre,         \n");
		sb.append("    book_name,          \n");
		sb.append("    isbn,               \n");
		sb.append("    book_pub_date,      \n");
		sb.append("    publisher,          \n");
		sb.append("    author,             \n");
		sb.append("    book_info,          \n");
		sb.append("    reg_id,             \n");
		sb.append("    reg_dt,             \n");
		sb.append("    mod_id,             \n");
		sb.append("    mod_dt              \n");
		sb.append(") VALUES (              \n");
		sb.append("    b_num_seq.NEXTVAL,  \n");
		sb.append("    ?,                  \n");
		sb.append("    ?,                  \n");
		sb.append("    TRUNC(DBMS_RANDOM.VALUE(1000000000000,9999999999999)),\n");
		sb.append("    ?,                  \n");
		sb.append("    ?,                  \n");
		sb.append("    ?,                  \n");
		sb.append("    ?,                  \n");
		sb.append("    ?,                  \n");
		sb.append("    SYSDATE,            \n");
		sb.append("    ?,                  \n");
		sb.append("    SYSDATE             \n");
		sb.append("   )                    \n");

		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.param : {}", param);
		
		try {
			conn.setAutoCommit(true);
			
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			pstmt.setInt(1, param.getBookGenre());
			pstmt.setString(2, param.getBookName());
			
			pstmt.setString(3, param.getBookPubDate());
			pstmt.setString(4, param.getPublisher());
			pstmt.setString(5, param.getAuthor());
			pstmt.setString(6, param.getBookInfo());
			pstmt.setString(7, param.getRegId());
			pstmt.setString(8, param.getModId());
			
			flag = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(conn, pstmt);// 자원반납
			
			log.debug("5.finally conn : {} pstmt : {} ", conn, pstmt);
		}
		log.debug("6.flag : {}", flag);
		return flag;
	}

	@Override
	public int doUpdate(BookDTO param) {
		
		int flag = 0;
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE book           \n");
		sb.append("SET                   \n");
		sb.append("    book_genre = ?,   \n");
		sb.append("    book_name = ?,    \n");
		sb.append("    isbn = ?,         \n");
		sb.append("    book_pub_date = ?,\n");
		sb.append("    publisher = ?,    \n");
		sb.append("    author = ?,       \n");
		sb.append("    book_info = ?,    \n");
		sb.append("    mod_id = ?,       \n");
		sb.append("    mod_dt = SYSDATE  \n");
		sb.append("WHERE                 \n");
		sb.append("  book_code = ?       \n");
		
		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.param : {}", param);
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			pstmt.setInt(1, param.getBookGenre());
			pstmt.setString(2, param.getBookName());
			pstmt.setLong(3, param.getIsbn());
			pstmt.setString(4, param.getBookPubDate());
			pstmt.setString(5, param.getPublisher());
			pstmt.setString(6, param.getAuthor());
			pstmt.setString(7, param.getBookInfo());
			pstmt.setString(8, param.getModId());
			pstmt.setInt(9, param.getBookCode());
			
			//DML
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
	public int doDelete(BookDTO param) {
		
		int flag = 0;
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM book     \n");
		sb.append("WHERE book_code = ?  \n");
		
		log.debug("1.sql : {}", sb.toString());
		log.debug("2.conn : {}", conn);
		log.debug("3.param : {}", param);
		
		try {
			
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4.pstmt : {}", pstmt);
			
			pstmt.setInt(1, param.getBookCode());
			
			flag = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, pstmt);
			log.debug("5.finally conn : {} pstmt : {} ", conn, pstmt);
		}
		log.debug("6.flag : {}", flag);
		return flag;
	}

	public BookDTO doSelect(BookDTO param) {
		
		BookDTO outVO = null;
	    Connection conn	= connectionMaker.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT                  \n");
		sb.append("    book_code,          \n");
		sb.append("    book_genre,         \n");
		sb.append("    book_name,          \n");
		sb.append("    isbn,               \n");
		sb.append("    TO_CHAR(book_pub_date,'YYYY/MM/DD') AS book_pub_date,\n");
		sb.append("    publisher,          \n");
		sb.append("    author,             \n");
		sb.append("    book_info           \n");
		sb.append("FROM                    \n");
		sb.append("    book                \n");
		sb.append("    WHERE book_code = ? \n");
		
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
				outVO = new BookDTO();
				
				outVO.setBookCode(rs.getInt("book_code"));
				outVO.setBookGenre(rs.getInt("book_genre"));
				outVO.setBookName(rs.getString("book_name"));
				outVO.setIsbn(rs.getLong("isbn"));
				outVO.setBookPubDate(rs.getString("book_pub_date"));
				outVO.setPublisher(rs.getString("publisher"));
				outVO.setAuthor(rs.getString("author"));
				outVO.setBookInfo(rs.getString("book_info"));
				
				log.debug("6.outVO : {}", outVO);
				
			}
			
		} catch (SQLException e) {
			log.debug("────────────────────────────────");
			log.debug("SQLException:" + e.getMessage());
			log.debug("────────────────────────────────");
		} finally {
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


	@Override
	public BookDTO doSelectOne(BookDTO param) {
		// TODO Auto-generated method stub
		return null;
	}


}
