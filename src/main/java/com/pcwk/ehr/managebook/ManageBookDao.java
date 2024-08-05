package com.pcwk.ehr.managebook;

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

public class ManageBookDao implements WorkDiv<ManageBookDTO>, PLog {

	private ConnectionMaker connectionMaker;
	
	public ManageBookDao() {
		connectionMaker = new ConnectionMaker();
	}

	@Override
	public List<ManageBookDTO> doRetrieve(DTO search) {
		// 1. DriverManager
		// 2. Connection
		// 3. Statement/preparedStatement
		// 4. ResultSet
		// 5. 연결종료
		SearchDTO searchVO = (SearchDTO)search;
    	log.debug("searchVO : {}", searchVO);
		StringBuilder sb = new StringBuilder(1000);
		StringBuilder sbWhere = new StringBuilder(1000);
		StringBuilder sbAnd = new StringBuilder(1000);

		if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("10")) {
			sbWhere.append("WHERE BOOK_NAME LIKE ?||'%' \n");
		}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("20")) {
			sbWhere.append("WHERE GENRE_NAME LIKE ?||'%' \n");			
		}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("30")) {
			sbWhere.append("WHERE AUTHOR LIKE ?||'%' \n");			
		}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("40")) {
			sbWhere.append("WHERE PUBLISHER LIKE ?||'%' \n");		
		}else {			
			sbWhere.append("WHERE BOOK_NAME LIKE '%' \n");		
		}
		
		if (null != searchVO.getRentYn() && searchVO.getRentYn().equals("10")) {
			sbAnd.append("AND (c.RENT_DATE IS NULL OR c.RETURNED_DATE IS NOT NULL) \n"); // 가능한 도서만 필터링
		}else if (null != searchVO.getRentYn() && searchVO.getRentYn().equals("20")) {
			sbAnd.append("AND (c.RENT_DATE IS NOT NULL AND c.RETURNED_DATE IS NULL) \n"); // 불가능한 도서만 필터링
		}else {
			sbAnd.append(""); // 필터링 없음
		}		
		log.debug("sbAnd : {}", sbAnd);	
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null; // SQL+PARAM
		ResultSet	rs			= null; // SQL문의 결과
		
		List<ManageBookDTO> list = new ArrayList<ManageBookDTO>();

		sb.append("SELECT A.*, B.totalCnt															\n");
		sb.append("FROM(	                                                                        \n");
		sb.append("SELECT *																			\n");
		sb.append("FROM (                                                                           \n");
		sb.append("    SELECT                                                                       \n");
		sb.append("        ROW_NUMBER() OVER (ORDER BY a.BOOK_CODE) AS RNUM,                        \n");
		sb.append("        a.BOOK_CODE,                                                             \n");
		sb.append("        a.BOOK_NAME,                                                             \n");
		sb.append("        a.BOOK_GENRE,                                                            \n");
		sb.append("        b.GENRE_NAME,                                                            \n");
		sb.append("        a.AUTHOR,                                                                \n");
		sb.append("        a.PUBLISHER,                                                             \n");
		sb.append("        NVL(TO_CHAR(c.RENT_DATE, 'YY/MM/DD'), '없음') AS RENT_DATE,              \n");
		sb.append("        NVL(TO_CHAR(c.DUE_DATE, 'YY/MM/DD'), '없음') AS DUE_DATE,                \n");
		sb.append("        NVL(TO_CHAR(c.RETURNED_DATE, 'YY/MM/DD'), '없음') AS RETURNED_DATE,      \n");
		sb.append("        CASE                                                                     \n");
		sb.append("            WHEN c.RENT_DATE IS NOT NULL AND c.RETURNED_DATE IS NULL THEN '불가능'\n");
		sb.append("            ELSE '가능'                                                          \n");
		sb.append("        END AS RENTYN,                                                           \n");
		sb.append("        NVL(c.NORETURN_COUNT, '대출여부없음') AS NORETURN_COUNT                  \n");	 
		sb.append("    FROM BOOK a                                                                  \n");
		sb.append("    LEFT JOIN (                                                                  \n");
		sb.append("        SELECT * FROM RENT WHERE RETURNED_DATE IS NULL                           \n");
		sb.append("    ) c ON a.BOOK_CODE = c.BOOK_CODE                                             \n");
		sb.append("    LEFT JOIN B_GENRE b ON a.BOOK_GENRE = b.BOOK_GENRE                           \n");
//		sb.append("    WHERE BOOK_NAME LIKE '%'                                                 	\n");
//		sb.append("    AND (c.RENT_DATE IS NULL OR c.RETURNED_DATE IS NOT NULL)                 	\n");
		sb.append(sbWhere.toString());				
		sb.append(sbAnd.toString());	
		sb.append("    ORDER BY a.MOD_DT	                                                        \n");
		sb.append(") subquery                                                                       \n");
//		sb.append("WHERE RNUM <= 10   							 						         	\n");
//		sb.append("WHERE RNUM <= ( :pageSize * (:pageNo -1)+:pageSize)							 	\n");		
		sb.append("WHERE RNUM <= (? * (? - 1) + ?)                                                   \n");
//		sb.append("AND rnum >= 1)A,                                                                  \n");
//		sb.append("AND rnum >= ( :pageSize * (:pageNo -1) + 1))A,									 \n");			
		sb.append("AND RNUM >= (? * (? - 1) + 1))A,                                                  \n");	
		sb.append("(                                                                                \n");
		sb.append("    SELECT COUNT(*) AS totalCnt                                                  \n");
		sb.append("    FROM(                                                                        \n");
		sb.append("        SELECT DISTINCT a.BOOK_CODE                                              \n");
		sb.append("        FROM BOOK a                                                              \n");
		sb.append("        LEFT JOIN (                                                              \n");    
		sb.append("        SELECT * FROM RENT WHERE RETURNED_DATE IS NULL                           \n");
		sb.append("        ) c ON a.BOOK_CODE = c.BOOK_CODE                                         \n");
		sb.append("        LEFT JOIN B_GENRE b ON a.BOOK_GENRE = b.BOOK_GENRE                       \n");    
//		sb.append("    WHERE BOOK_NAME LIKE '%'                                                 	 \n");
//		sb.append("    AND (c.RENT_DATE IS NULL OR c.RETURNED_DATE IS NOT NULL)                 	 \n");
		sb.append(sbWhere.toString());				
		sb.append(sbAnd.toString());	
		sb.append("    )cnt_query                                                                   \n");
		sb.append(")B                                                                               \n");	
		
		log.debug("1.sql: {} \n", sb.toString());
		log.debug("2.conn: {} \n", conn);
		log.debug("3.param: {}", search);
		
		// Select
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);
			
			// param 설정
			
			// paging
			if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("10")) {
				log.debug("4.1 searchDiv : {}", searchVO.getSearchDiv());
				// 검색어
				pstmt.setString(1, searchVO.getSearchWord());
				// ROWNUM
				pstmt.setInt(2, searchVO.getPageSize());
				pstmt.setInt(3, searchVO.getPageNo());
				pstmt.setInt(4, searchVO.getPageSize());
				
				// rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				// 검색어
				pstmt.setString(7, searchVO.getSearchWord());
			}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("20")) {
				log.debug("4.1 searchDiv : {}", searchVO.getSearchDiv());
				// 검색어
				pstmt.setString(1, searchVO.getSearchWord());
				// ROWNUM
				pstmt.setInt(2, searchVO.getPageSize());
				pstmt.setInt(3, searchVO.getPageNo());
				pstmt.setInt(4, searchVO.getPageSize());
				
				// rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				// 검색어
				pstmt.setString(7, searchVO.getSearchWord());
			}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("30")) {
				log.debug("4.1 searchDiv : {}", searchVO.getSearchDiv());
				// 검색어
				pstmt.setString(1, searchVO.getSearchWord());
				// ROWNUM
				pstmt.setInt(2, searchVO.getPageSize());
				pstmt.setInt(3, searchVO.getPageNo());
				pstmt.setInt(4, searchVO.getPageSize());
				
				// rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				// 검색어
				pstmt.setString(7, searchVO.getSearchWord());
			}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("40")) {
				log.debug("4.1 searchDiv : {}", searchVO.getSearchDiv());
				// 검색어
				pstmt.setString(1, searchVO.getSearchWord());
				// ROWNUM
				pstmt.setInt(2, searchVO.getPageSize());
				pstmt.setInt(3, searchVO.getPageNo());
				pstmt.setInt(4, searchVO.getPageSize());
				
				// rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				// 검색어
				pstmt.setString(7, searchVO.getSearchWord());
			}else {
				// ROWNUM
				pstmt.setInt(1, searchVO.getPageSize());
				pstmt.setInt(2, searchVO.getPageNo());
				pstmt.setInt(3, searchVO.getPageSize());
				
				// rnum
				pstmt.setInt(4, searchVO.getPageSize());
				pstmt.setInt(5, searchVO.getPageNo());
			}
			
			// SELECT실행
			rs = pstmt.executeQuery();
			log.debug("5.rs:\n" + rs);
			while (rs.next()) {
				// 건수 최대값만 정해짐
				ManageBookDTO outVO = new ManageBookDTO();
				
				outVO.setRnum(rs.getInt("RNUM"));
				outVO.setBookCode(rs.getInt("BOOK_CODE"));
				outVO.setBookName(rs.getString("BOOK_NAME"));
				outVO.setGenre(rs.getString("GENRE_NAME"));
				outVO.setAuthor(rs.getString("AUTHOR"));
				outVO.setPublisher(rs.getString("PUBLISHER"));
				outVO.setRentDate(rs.getString("RENT_DATE"));
				outVO.setDueDate(rs.getString("DUE_DATE"));
				outVO.setRetunredDate(rs.getString("RETURNED_DATE"));
				outVO.setRentYn(rs.getString("RENTYN"));
				outVO.setNoreturnCount(rs.getString("NORETURN_COUNT"));
				outVO.setTotalCnt(rs.getInt("totalCnt"));
				
				list.add(outVO);
			} // while
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(conn, pstmt, rs); 
			log.debug("5. finally conn : {} pstmt : {} rs : {}",conn, pstmt, rs);
		}// try catch finally
		
		return list;
		
	} // doRetrieve 끝

	@Override
	public int doDelete(ManageBookDTO param) {
		int flag = 0;
		Connection conn = connectionMaker.getConnection();
		
		PreparedStatement pstmt = null; // SQL + PARAM		
		StringBuilder sb = new StringBuilder();
		// 아래 SQL코드에는 세미콜론(;) 금지
		sb.append("DELETE 	FROM BOOK	   \n");
		sb.append("WHERE 	BOOK_CODE = ?  \n");	
		
		log.debug("1. sql : {}", sb.toString());
		log.debug("2. conn : {}", conn);
		log.debug("3. param : {}", param);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);
			
			pstmt.setInt(1, param.getBookCode());
			
			// DML 수행
			flag = pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(conn, pstmt);
			
			log.debug("5. finally conn : {} pstmt : {}", conn, pstmt);
		}// try catch
		log.debug("6. flag: {}", flag);
		
		return flag;
	}
	
	@Override
	public ManageBookDTO doSelectOne(ManageBookDTO param) {
		ManageBookDTO outVO = null; // 단건조회 결과
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null; // SQL+PARAM
		ResultSet	rs			= null; // SQL문의 결과
		
		StringBuilder sb = new StringBuilder(500);
		
		sb.append("SELECT c.RENT_CODE,      														\n");                                                       
		sb.append("		  a.BOOK_CODE,      														\n");                                                       
		sb.append("       a.BOOK_NAME,                                                              \n");
		sb.append("       a.BOOK_GENRE,                                                             \n");
		sb.append("       b.GENRE_NAME,                                                             \n");
		sb.append("       a.AUTHOR,                                                                 \n");
		sb.append("       a.PUBLISHER,                                                              \n");
		sb.append("       a.ISBN,                                                              		\n");
		sb.append("       TO_CHAR(TO_DATE(a.BOOK_PUB_DATE, 'YY/MM/DD')) AS BOOK_PUB_DATE,  		    \n");
		sb.append("       a.BOOK_INFO,                                                        		\n");
		sb.append("               NVL(TO_CHAR(c.RENT_DATE, 'YY/MM/DD'), '없음') AS RENT_DATE,       	\n");
		sb.append("               NVL(TO_CHAR(c.DUE_DATE, 'YY/MM/DD'), '없음') AS DUE_DATE,         	\n");
		sb.append("               NVL(TO_CHAR(c.RETURNED_DATE, 'YY/MM/DD'), '없음') AS RETURNED_DATE,\n");		
		sb.append("       CASE                                                                      \n");
		sb.append("           WHEN c.RENT_DATE IS NOT NULL AND c.RETURNED_DATE IS NULL THEN '불가능'	\n");
		sb.append("           ELSE '가능'                                                           	\n");
		sb.append("       END AS RENTYN,                                                            \n");
		sb.append("       NVL(c.NORETURN_COUNT, '대출여부없음') AS NORETURN_COUNT           			\n");
		sb.append("FROM BOOK a                                                                      \n");
		sb.append("LEFT JOIN RENT c ON a.BOOK_CODE = c.BOOK_CODE                                    \n");
		sb.append("LEFT JOIN B_GENRE b ON a.BOOK_GENRE = b.BOOK_GENRE                               \n");
		sb.append("WHERE a.BOOK_CODE = ?                                                            \n");
		
		log.debug("1.sql: {} \n", sb.toString());
		log.debug("2.conn: {} \n", conn);
		log.debug("3.param: {}", param);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);
			
			pstmt.setInt(1, param.getBookCode());
			
			// SELECT실행
			rs = pstmt.executeQuery();
			log.debug("5.rs:\n" + rs);
			
			if(rs.next()) {
				outVO = new ManageBookDTO();
				
				outVO.setRentCode(rs.getInt("RENT_CODE"));
				outVO.setBookCode(rs.getInt("BOOK_CODE"));
				outVO.setBookName(rs.getString("BOOK_NAME"));
				outVO.setBookGenre(rs.getInt("BOOK_GENRE"));
				outVO.setGenre(rs.getString("GENRE_NAME"));
				outVO.setAuthor(rs.getString("AUTHOR"));
				outVO.setPublisher(rs.getString("PUBLISHER"));
				outVO.setIsbn(rs.getString("ISBN"));
				outVO.setBookPubDate(rs.getString("BOOK_PUB_DATE"));
				outVO.setBookInfo(rs.getString("BOOK_INFO"));
				outVO.setRentDate(rs.getString("RENT_DATE"));
				outVO.setDueDate(rs.getString("DUE_DATE"));
				outVO.setRetunredDate(rs.getString("RETURNED_DATE"));
				outVO.setRentYn(rs.getString("RENTYN"));
				outVO.setNoreturnCount(rs.getString("NORETURN_COUNT"));
				
				log.debug("6.outVO:" + outVO);
			} // if
			
		} catch (SQLException e) {
			log.debug("────────────────────────────");
			log.debug("SQLException : " + e.getMessage());
			log.debug("────────────────────────────");
		} finally{
			DBUtil.close(conn, pstmt, rs); 
		}// try catch finally		
		return outVO;
	} // doSelectOne 끝

	@Override
	public int doSave(ManageBookDTO param) {
		int flag = 0;
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null; // SQL + PARAM
		
		StringBuilder sb = new StringBuilder();
		// 아래 SQL코드에는 세미콜론(;) 금지
		sb.append("INSERT INTO BOOK(					\n");
		sb.append("    BOOK_NAME,                       \n");
		sb.append("    BOOK_GENRE,                      \n");
		sb.append("    ISBN,                            \n");
		sb.append("    BOOK_PUB_DATE,                   \n");
		sb.append("    PUBLISHER,                       \n");
		sb.append("    AUTHOR,                          \n");
		sb.append("    BOOK_INFO,                       \n");
		sb.append("    REG_ID,                          \n");
		sb.append("    MOD_ID                           \n");
		sb.append(")                                    \n");
		sb.append("VALUES(                              \n");
		sb.append("    ?,                         		\n");
		sb.append("    ?,	                            \n");
		sb.append("    ?,                   			\n");
		sb.append("    ?, 								\n");
		sb.append("    ?,		                        \n");
		sb.append("    ?,           		            \n");
		sb.append("    ?,	                            \n");
		sb.append("    ?,                       		\n");
		sb.append("    ?                         		\n");
		sb.append(")                                    \n");	
		
		log.debug("1. sql : {}", sb.toString());
		log.debug("2. conn : {}", conn);
		log.debug("3. param : {}", param);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);
			
			// param 설정
			pstmt.setString(1, param.getBookName());
			pstmt.setInt(2, param.getBookGenre());
			pstmt.setString(3, param.getIsbn());
			pstmt.setString(4, param.getBookPubDate());
			pstmt.setString(5, param.getPublisher());
			pstmt.setString(6, param.getAuthor());
			pstmt.setString(7, param.getBookInfo());
			pstmt.setString(8, param.getRegId());
			pstmt.setString(9, param.getRegId());
			
			// DML
			flag = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(conn, pstmt);
			
			log.debug("5. finally conn : {} pstmt : {}", conn, pstmt);
		}// try catch
		log.debug("6. flag: {}", flag);
		
		return flag;
	}

	@Override
	public int doUpdate(ManageBookDTO param) {
		int flag = 0;
		Connection conn = connectionMaker.getConnection();
		
		PreparedStatement pstmt = null; // SQL + PARAM		
		StringBuilder sb = new StringBuilder(500);
		// 아래 SQL코드에는 세미콜론(;) 금지
		sb.append("UPDATE  BOOK				 \n");
		sb.append("SET     BOOK_NAME = ?,    \n");
		sb.append("        BOOK_GENRE = ?,   \n");
		sb.append("        AUTHOR = ?,       \n");
		sb.append("        PUBLISHER = ?,    \n");
		sb.append("        ISBN = ?,         \n");
		sb.append("        BOOK_PUB_DATE = ?,\n");
		sb.append("        BOOK_INFO = ?,    \n");
		sb.append("        MOD_ID = ?     	 \n");
		sb.append("WHERE   BOOK_CODE = ?     \n");
		
		log.debug("1. sql : {}", sb.toString());
		log.debug("2. conn : {}", conn);
		log.debug("3. param : {}", param);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);

			pstmt.setString(1, param.getBookName());
			pstmt.setInt(2, param.getBookGenre());
			pstmt.setString(3, param.getAuthor());
			pstmt.setString(4, param.getPublisher());
			pstmt.setString(5, param.getIsbn());
			pstmt.setString(6, param.getBookPubDate());
			pstmt.setString(7, param.getBookInfo());
			pstmt.setString(8, param.getModId());
			pstmt.setInt(9, param.getBookCode());
			
			// DML 수행
			flag = pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(conn, pstmt);
			
			log.debug("5. finally conn : {} pstmt : {}", conn, pstmt);
		}// try catch
		log.debug("6. flag: {}", flag);
		
		return flag;
	}
	
	public int doDueDateUpdate(ManageBookDTO param) {
		int flag = 0;
		Connection conn = connectionMaker.getConnection();
		
		PreparedStatement pstmt = null; // SQL + PARAM		
		StringBuilder sb = new StringBuilder(500);
		// 아래 SQL코드에는 세미콜론(;) 금지
		sb.append("UPDATE RENT					\n");
		sb.append("SET DUE_DATE = DUE_DATE + 7, \n");
		sb.append("    NORETURN_COUNT = 'N',    \n");
		sb.append("    MOD_ID = ?     			\n");
		sb.append("WHERE NORETURN_COUNT = 'Y'   \n");
		sb.append("AND   RENT_CODE = ? 	        \n");
		
		log.debug("1. sql : {}", sb.toString());
		log.debug("2. conn : {}", conn);
		log.debug("3. param : {}", param);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);

			pstmt.setString(1, param.getModId());
			pstmt.setInt(2, param.getRentCode());
			
			// DML 수행
			flag = pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(conn, pstmt);
			
			log.debug("5. finally conn : {} pstmt : {}", conn, pstmt);
		}// try catch
		log.debug("6. flag: {}", flag);
		
		return flag;
	}
	
	public int doReturned(ManageBookDTO param) {
		int flag = 0;
		Connection conn = connectionMaker.getConnection();
		
		PreparedStatement pstmt = null; // SQL + PARAM		
		StringBuilder sb = new StringBuilder(500);
		// 아래 SQL코드에는 세미콜론(;) 금지
		sb.append("UPDATE RENT					  	\n");	
		sb.append("SET    RETURNED_DATE = SYSDATE,	\n");
		sb.append("   	  MOD_ID = ?				\n");
		sb.append("WHERE  RENT_CODE = ?				\n");
		sb.append("AND 	  RETURNED_DATE IS NULL		\n");
		
		log.debug("1. sql : {}", sb.toString());
		log.debug("2. conn : {}", conn);
		log.debug("3. param : {}", param);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);

			pstmt.setString(1, param.getModId());
			pstmt.setInt(2, param.getRentCode());
			
			// DML 수행
			flag = pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(conn, pstmt);			
			log.debug("5. finally conn : {} pstmt : {}", conn, pstmt);
		}// try catch
		log.debug("6. flag: {}", flag);
		
		return flag;
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

} // class
