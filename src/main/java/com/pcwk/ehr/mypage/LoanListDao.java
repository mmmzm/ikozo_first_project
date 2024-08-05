package com.pcwk.ehr.mypage;

import java.sql.Connection;


import java.sql.DriverManager;
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

//Dao : 화면보고 화면에 나오는 것들을 짜기
public class LoanListDao implements WorkDiv<LoanListDTO>, PLog {
	private ConnectionMaker connectionMaker;
	
	public LoanListDao() {
		connectionMaker=new ConnectionMaker();
	}

	
	@Override	
	public List<LoanListDTO> doRetrieve(DTO search) {
		SearchDTO searchVO = (SearchDTO)search;
		
		//검색 조건에 대한 StringBuilder
		StringBuilder sb = new StringBuilder(1000);
		StringBuilder sbAnd = new StringBuilder(200);
		
		if (null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("10")) {
			sbAnd.append("AND book_name LIKE ?||'%' \n");
		}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("20")) {
			sbAnd.append("AND genre_name LIKE ?||'%' \n");			
		}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("30")) {
			sbAnd.append("AND author LIKE ?||'%' \n");			
		}

		
		Connection conn = connectionMaker.getConnection();
		PreparedStatement pstmt = null; // SQL+PARAM
		ResultSet	rs			= null; // SQL문의 결과
		
		List<LoanListDTO> list = new ArrayList<LoanListDTO>();
		
		sb.append(" select b.*, c.*                                                                                         \n");
		sb.append(" from(                                                                                                   \n");
		sb.append("     select a.*                                                                                          \n");
		sb.append("     from(                                                                                               \n");
		sb.append("         select tt1.book_name, tt2.genre_name, tt1.author,  TO_CHAR(tt1.rent_date,'YYYY-MM-DD')rent_date,\n");
		sb.append("                TO_CHAR(tt1.due_date,'YYYY-MM-DD')due_date, tt1.extra_sum, rownum as num                 \n");
		sb.append("         from (                                                                                          \n");
		sb.append("                 select t1.book_name as book_name, t1.author as author , t1.book_genre as book_genre,    \n");
		sb.append("                         t2.rent_date as rent_date, t2.due_date as due_date, t2.extra_sum as extra_sum,  \n");
		sb.append("                         t2.user_id as user_id                                                           \n");
		sb.append("                 from book t1, rent t2                                                                   \n");
		sb.append("                 where t1.book_code=t2.book_code                                                         \n");
		sb.append("                 )TT1, b_genre tt2                                                                       \n");
		sb.append("         where tt1.book_genre=tt2.book_genre                                                             \n");
//		sb.append("         and rownum <=10                                                                                 \n");
//		sb.append(" 	    and ROWNUM <=(:pageSize * (:pageNo -1)+:pageSize)                                               \n");		
		sb.append("         and rownum<= ( ? * (? - 1)+?)                                                                   \n");
//		sb.append("         and tt2.genre_name LIKE '소설%'                                                                 \n");
		//--WHERE-------------------------------------------------------
		sb.append(sbAnd.toString());
		sb.append("         )a                                                                                              \n");
//		sb.append("     where num>=1                                                                                        \n");
//		sb.append(" 	WHERE num >=(:pageSize * (:pageNo -1)+1)                                                            \n");		
		sb.append("     where  num>= (? * (? -1) + 1)                                                                       \n");
		sb.append("     )b,(                                                                                                \n");
		sb.append("             select count(*)totalCnt                                                                     \n");
		sb.append("             from book m1, b_genre m2                                                                    \n");
		sb.append("             where m1.book_genre=m2.book_genre                                                           \n");
//		sb.append("             and m2.genre_name LIKE '소설%'                                                               \n");
		//--WHERE-------------------------------------------------------
		sb.append(sbAnd.toString());
		sb.append("         )c                                                                                              \n");
		

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
				// ROWNUM
				pstmt.setInt(1, searchVO.getPageSize());
				pstmt.setInt(2, searchVO.getPageNo());
				pstmt.setInt(3, searchVO.getPageSize());
				
				//검색어 : 책이름
				pstmt.setString(4, searchVO.getSearchWord());
				
				// rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				
				//검색어 : 책이름
				pstmt.setString(7, searchVO.getSearchWord());
			}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("20")) {
				log.debug("4.1 searchDiv : {}", searchVO.getSearchDiv());
				// ROWNUM
				pstmt.setInt(1, searchVO.getPageSize());
				pstmt.setInt(2, searchVO.getPageNo());
				pstmt.setInt(3, searchVO.getPageSize());
				
				// 검색어 : 장르이름
				pstmt.setString(4, searchVO.getSearchWord());
				
				// rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				
				// 검색어 : 장르이름
				pstmt.setString(7, searchVO.getSearchWord());

			}else if(null != searchVO.getSearchDiv() && searchVO.getSearchDiv().equals("30")) {
				log.debug("4.1 searchDiv : {}", searchVO.getSearchDiv());
				// ROWNUM
				pstmt.setInt(1, searchVO.getPageSize());
				pstmt.setInt(2, searchVO.getPageNo());
				pstmt.setInt(3, searchVO.getPageSize());
				
				// 검색어 : 저자이름
				pstmt.setString(4, searchVO.getSearchWord());
				
				// rnum
				pstmt.setInt(5, searchVO.getPageSize());
				pstmt.setInt(6, searchVO.getPageNo());
				// 검색어 : 저자이름
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
				LoanListDTO outVO = new LoanListDTO();
				
				outVO.setNum(rs.getInt("num"));
				outVO.setBookName(rs.getString("BOOK_NAME"));
				outVO.setGenreName(rs.getString("GENRE_NAME"));
				outVO.setAuthor(rs.getString("AUTHOR"));
				outVO.setRentDate(rs.getString("rent_date"));
				outVO.setDueDate(rs.getString("due_date"));
				outVO.setExtraSum(rs.getInt("extra_sum"));
				
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
	}//doLoanlist end


	@Override
	public int doSave(LoanListDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int doUpdate(LoanListDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int doDelete(LoanListDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public LoanListDTO doSelectOne(LoanListDTO param) {
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


}//class end
