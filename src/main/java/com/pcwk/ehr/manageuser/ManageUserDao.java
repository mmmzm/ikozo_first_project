package com.pcwk.ehr.manageuser;

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

public class ManageUserDao implements WorkDiv<ManageUserDTO>, PLog {

	private ConnectionMaker connectionMaker;
	
	public ManageUserDao() {
		connectionMaker = new ConnectionMaker();
	}

	@Override
	public List<ManageUserDTO> doRetrieve(DTO search) {
		// 1. DriverManager
		// 2. Connection
		// 3. Statement/preparedStatement
		// 4. ResultSet
		// 5. 연결종료
		SearchDTO searchVO = (SearchDTO)search;

		StringBuilder sb = new StringBuilder(1000);
		StringBuilder sbWhere = new StringBuilder(1000);
		StringBuilder sbAnd = new StringBuilder(1000);

		if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("10")) {
			sbWhere.append("WHERE a.USER_ID LIKE ?||'%' \n");
		}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("20")) {
			sbWhere.append("WHERE a.USER_NAME LIKE ?||'%' \n");			
		}else {
			sbWhere.append("WHERE a.USER_NAME LIKE '%' \n");	
		}

		if (null != searchVO.getIsAdmin() && searchVO.getIsAdmin().equals("10")) {
			sbAnd.append("AND DECODE(a.IS_ADMIN, 'Y', '관리자', '회원') = '관리자' \n"); // 관리자만 필터링
		}else if (null != searchVO.getIsAdmin() && searchVO.getIsAdmin().equals("20")) {
			sbAnd.append("AND DECODE(a.IS_ADMIN, 'Y', '관리자', '회원') = '회원' \n"); // 회원만 필터링
		}else {
			sbAnd.append(""); // 필터링 없음
		}
		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null; // SQL+PARAM
		ResultSet	rs			= null; // SQL문의 결과
		
		List<ManageUserDTO> list = new ArrayList<ManageUserDTO>();

		sb.append("SELECT A.*, B.totalCnt																			\n");
		sb.append("FROM (                                                                                           \n");
		sb.append("    SELECT RNUM, USER_ID, USER_NAME, IS_ADMIN, EXTRA_SUM, RENTYN                                 \n");
		sb.append("    FROM (                                                                                       \n");
		sb.append("        SELECT ROWNUM AS RNUM, subquery.*                                                        \n");
		sb.append("        FROM (                                                                                   \n");
		sb.append("            SELECT  a.USER_ID AS USER_ID,                                                        \n");
		sb.append("                    a.USER_NAME AS USER_NAME,                                                    \n");
		sb.append("                    DECODE(a.IS_ADMIN, 'Y', '관리자', '회원') AS IS_ADMIN,                         \n");
		sb.append("                    NVL(b.EXTRA_SUM, 0) AS EXTRA_SUM,                                            \n");
		sb.append("                    CASE                                                                         \n");
		sb.append("                        WHEN b.USER_ID IS NOT NULL AND b.returned_date IS NULL THEN '미납'        \n");
		sb.append("                        ELSE '없음'                                                               \n");
		sb.append("                    END AS RENTYN                                                                \n");
		sb.append("            FROM    LIB_USER a                                                                   \n");
		sb.append("            LEFT JOIN (                                                                          \n");
		sb.append("                SELECT USER_ID, EXTRA_SUM, RETURNED_DATE                                         \n");
		sb.append("                FROM (                                                                           \n");
		sb.append("                    SELECT USER_ID, EXTRA_SUM, RETURNED_DATE,                                    \n");
		sb.append("                           ROW_NUMBER() OVER (PARTITION BY USER_ID ORDER BY DUE_DATE DESC) AS rn \n");
		sb.append("                    FROM RENT                                                                    \n");
		sb.append("                )                                                                                \n");
		sb.append("                WHERE rn = 1	                                                                    \n");
		sb.append("            ) b ON a.USER_ID = b.USER_ID                                                         \n");
//		sb.append("      	  WHERE USER_NAME LIKE '최진서%'                                                        \n");
//		sb.append("       	 AND DECODE(a.IS_ADMIN, 'Y', '관리자', '회원') = '관리자'			                    \n");
		sb.append(sbWhere.toString());
		sb.append(sbAnd.toString());		
		sb.append("            ORDER BY a.MOD_DT                                                                    \n");
		sb.append("        ) subquery                                                                               \n");
//		sb.append("		WHERE ROWNUM <= 10                      										 		 	\n");
//		sb.append(" WHERE ROWNUM <= ( :pageSize * (:pageNo -1)+:pageSize)								 		 	\n");
		sb.append(" WHERE ROWNUM <= ( ? * (? -1)+?)								 								 	\n");
		sb.append("    )                                                                                            \n");
//		sb.append("	WHERE RNUM >= 1                              												 	\n");
//		sb.append("	WHERE RNUM >= ( :pageSize * (:pageNo -1) + 1)												 	\n");
		sb.append("	WHERE RNUM >= ( ? * (? -1) + 1)										 							\n");
		sb.append(") A,                                                                                             \n");
		sb.append("(                                                                                                \n");
		sb.append("    SELECT COUNT(*) AS totalCnt                                                                  \n");
		sb.append("    FROM (                                                                                       \n");
		sb.append("        SELECT DISTINCT a.USER_ID                                                                \n");
		sb.append("        FROM LIB_USER a                                                                          \n");
		sb.append("        LEFT JOIN RENT b ON a.USER_ID = b.USER_ID                                                \n");
//		sb.append("        WHERE USER_NAME LIKE '최진서%'                                                        	\n");
//		sb.append("        AND DECODE(a.IS_ADMIN, 'Y', '관리자', '회원') = '관리자'			                     	\n");
		sb.append(sbWhere.toString());
		sb.append(sbAnd.toString());		
		sb.append("    ) cnt_query                                                                                  \n");
		sb.append(") B                                                                                             	\n");
		
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
				ManageUserDTO outVO = new ManageUserDTO();
				
				outVO.setRnum(rs.getInt("RNUM"));
				outVO.setUserId(rs.getString("USER_ID"));
				outVO.setUserName(rs.getString("USER_NAME"));
				outVO.setIsAdmin(rs.getString("IS_ADMIN"));
				outVO.setRentBookYn(rs.getString("RENTYN"));
				outVO.setExtraSum(rs.getInt("EXTRA_SUM"));
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
	}

	@Override
	public int doDelete(ManageUserDTO param) {
		int flag = 0;
		Connection conn = connectionMaker.getConnection();
		
		PreparedStatement pstmt = null; // SQL + PARAM		
		StringBuilder sb = new StringBuilder();
		// 아래 SQL코드에는 세미콜론(;) 금지
		sb.append("DELETE 	FROM LIB_USER\n");
		sb.append("WHERE 	USER_ID = ?  \n");	
		
		log.debug("1. sql : {}", sb.toString());
		log.debug("2. conn : {}", conn);
		log.debug("3. param : {}", param);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);
			
			// 파라메터 설정
			pstmt.setString(1, param.getUserId());
			
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
	public ManageUserDTO doSelectOne(ManageUserDTO param) {
		ManageUserDTO outVO = null; // 단건조회 결과
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null; // SQL+PARAM
		ResultSet	rs			= null; // SQL문의 결과
		
		StringBuilder sb = new StringBuilder(500);
		
		sb.append("SELECT *																				  \n");
		sb.append("FROM (                                                                                 \n");
		sb.append("    SELECT  ROWNUM AS num,                                                             \n");
		sb.append("            subquery.*,                                                                \n");
		sb.append("            c.BOOK_NAME                                                                \n");
		sb.append("    FROM (                                                                             \n");
		sb.append("        SELECT  a.USER_ID,                                                             \n");
		sb.append("                a.USER_NAME,                                                           \n");
		sb.append("                DECODE(a.IS_ADMIN, 'Y', '관리자', '회원') AS IS_ADMIN,                    \n");
		sb.append("                NVL(b.EXTRA_SUM, 0) AS extra_sum,                                      \n");
		sb.append("                CASE                                                                   \n");
		sb.append("                    WHEN b.USER_ID IS NOT NULL AND b.returned_date IS NULL THEN '미납'  \n");
		sb.append("                    ELSE '없음'                                                         \n");
		sb.append("                END AS RENTYN,                                                         \n");
		sb.append("                b.BOOK_CODE                       									  \n");
		sb.append("        FROM    LIB_USER a                                                             \n");
		sb.append("        LEFT JOIN RENT b ON a.USER_ID = b.USER_ID                                      \n");
		sb.append("        WHERE   a.USER_ID = ? 		                                                  \n");
//		sb.append("        AND (b.USER_ID IS NULL OR b.returned_date IS NULL)                             \n"); // 미납인 경우만 필터링
		sb.append("    ) subquery                                                                         \n");
		sb.append("    LEFT JOIN BOOK c ON subquery.BOOK_CODE = c.BOOK_CODE                               \n");
		sb.append(") final_query                                                                          \n");
		sb.append("ORDER BY final_query.RENTYN ASC	                                                      \n");
		
		log.debug("1.sql: {} \n", sb.toString());
		log.debug("2.conn: {} \n", conn);
		log.debug("3.param: {}", param);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);
			
			pstmt.setString(1, param.getUserId());
			
			// SELECT실행
			rs = pstmt.executeQuery();
			log.debug("5.rs:\n" + rs);
			
			if(rs.next()) {
				outVO = new ManageUserDTO();
				
				outVO.setUserId(rs.getString("USER_ID"));
				outVO.setUserName(rs.getString("USER_NAME"));
				outVO.setIsAdmin(rs.getString("IS_ADMIN"));
				outVO.setExtraSum(rs.getInt("EXTRA_SUM"));
				outVO.setRentBookYn(rs.getString("RENTYN"));
				outVO.setBookCode(rs.getInt("BOOK_CODE"));
				outVO.setBookName(rs.getString("BOOK_NAME"));
				
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
	
//	@Override
//	public List<ManageUserDTO> doSelectOne(ManageUserDTO param) {
//		ManageUserDTO outVO = null; // 단건조회 결과
//
//		SearchDTO searchVO = (SearchDTO)search;		
//		
//		StringBuilder sb = new StringBuilder(500);
//		
//		Connection conn = connectionMaker.getConnection();
//		PreparedStatement pstmt = null; // SQL+PARAM
//		ResultSet	rs			= null; // SQL문의 결과
//		
//		List<ManageUserDTO> list = new ArrayList<ManageUserDTO>();			
//		
//		sb.append("SELECT *																				  \n");
//		sb.append("FROM (                                                                                 \n");
//		sb.append("    SELECT  ROWNUM AS num,                                                             \n");
//		sb.append("            subquery.*,                                                                \n");
//		sb.append("            c.BOOK_NAME                                                                \n");
//		sb.append("    FROM (                                                                             \n");
//		sb.append("        SELECT  a.USER_ID,                                                             \n");
//		sb.append("                a.USER_NAME,                                                           \n");
//		sb.append("                DECODE(a.IS_ADMIN, 'Y', '관리자', '회원') AS IS_ADMIN,                    \n");
//		sb.append("                NVL(b.EXTRA_SUM, 0) AS extra_sum,                                      \n");
//		sb.append("                CASE                                                                   \n");
//		sb.append("                    WHEN b.USER_ID IS NOT NULL AND b.returned_date IS NULL THEN '미납'  \n");
//		sb.append("                    ELSE '없음'                                                         \n");
//		sb.append("                END AS RENTYN,                                                         \n");
//		sb.append("                b.BOOK_CODE                       									  \n");
//		sb.append("        FROM    LIB_USER a                                                             \n");
//		sb.append("        LEFT JOIN RENT b ON a.USER_ID = b.USER_ID                                      \n");
//		sb.append("        WHERE   a.USER_ID = ? 		                                                  \n");
////		sb.append("        AND (b.USER_ID IS NULL OR b.returned_date IS NULL)                             \n"); // 미납인 경우만 필터링
//		sb.append("    ) subquery                                                                         \n");
//		sb.append("    LEFT JOIN BOOK c ON subquery.BOOK_CODE = c.BOOK_CODE                               \n");
//		sb.append(") final_query                                                                          \n");
//		sb.append("ORDER BY final_query.RENTYN ASC	                                                      \n");
//		
//		log.debug("1.sql: {} \n", sb.toString());
//		log.debug("2.conn: {} \n", conn);
//		log.debug("3.param: {}", param);
//		
//		try {
//			// conn.setAutoCommit(false); // 자동 커밋 정지
//			pstmt = conn.prepareStatement(sb.toString());
//			log.debug("4. pstmt : {}", pstmt);
//			
//			pstmt.setString(1, param.getUserId());
//			
//			// SELECT실행
//			rs = pstmt.executeQuery();
//			log.debug("5.rs:\n" + rs);
//			
//			if(rs.next()) {
//				outVO = new ManageUserDTO();
//				
//				outVO.setUserId(rs.getString("USER_ID"));
//				outVO.setUserName(rs.getString("USER_NAME"));
//				outVO.setIsAdmin(rs.getString("IS_ADMIN"));
//				outVO.setExtraSum(rs.getInt("EXTRA_SUM"));
//				outVO.setRentBookYn(rs.getString("RENTYN"));
//				outVO.setBookCode(rs.getInt("BOOK_CODE"));
//				outVO.setBookName(rs.getString("BOOK_NAME"));
//				
//				log.debug("6.outVO:" + outVO);
//			} // if
//			
//		} catch (SQLException e) {
//			log.debug("────────────────────────────");
//			log.debug("SQLException : " + e.getMessage());
//			log.debug("────────────────────────────");
//		} finally{
//			DBUtil.close(conn, pstmt, rs); 
//		}// try catch finally
//		
//		return outVO;
//	} // doSelectOne 끝

	@Override
	public int doUpdate(ManageUserDTO param) {
		int flag = 0;
		Connection conn = connectionMaker.getConnection();
		
		PreparedStatement pstmt = null; // SQL + PARAM		
		StringBuilder sb = new StringBuilder(500);
		// 아래 SQL코드에는 세미콜론(;) 금지
		sb.append("UPDATE RENT					  	\n");	
		sb.append("SET    RETURNED_DATE = SYSDATE	\n");
		sb.append("WHERE  RENT_CODE = ?				\n");
		
		log.debug("1. sql : {}", sb.toString());
		log.debug("2. conn : {}", conn);
		log.debug("3. param : {}", param);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);

			pstmt.setInt(1, param.getRentCode());
			
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

	public int doUpdateBack(ManageUserDTO param) {
		int flag = 0;
		Connection conn = connectionMaker.getConnection();
		
		PreparedStatement pstmt = null; // SQL + PARAM		
		StringBuilder sb = new StringBuilder(500);
		// 아래 SQL코드에는 세미콜론(;) 금지
		sb.append("UPDATE RENT					  	\n");	
		sb.append("SET    RETURNED_DATE = null		\n");
		sb.append("WHERE  RENT_CODE = ?				\n");
		
		log.debug("1. sql : {}", sb.toString());
		log.debug("2. conn : {}", conn);
		log.debug("3. param : {}", param);
		
		try {
			// conn.setAutoCommit(false); // 자동 커밋 정지
			pstmt = conn.prepareStatement(sb.toString());
			log.debug("4. pstmt : {}", pstmt);

			pstmt.setInt(1, param.getRentCode());
			
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
	} // 반납취소
	
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
	public int doSave(ManageUserDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

} // class
