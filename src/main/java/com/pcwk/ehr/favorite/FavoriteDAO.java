package com.pcwk.ehr.favorite;

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
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.cmn.SearchDTO;
public class FavoriteDAO implements WorkDiv<FavoriteDTO>, PLog {
    private ConnectionMaker connectionmaker;

    public FavoriteDAO() {
        connectionmaker = new ConnectionMaker();
    }

  
    

    @Override
    public int doDelete(FavoriteDTO param) {
        int flag = 0;
        Connection conn = connectionmaker.getConnection();
        PreparedStatement pstmt = null;

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM favorite \n");
        sb.append("WHERE fav_seq = ?    \n");

        log.debug("1.sql:{}", sb.toString());
        log.debug("2.conn:{}", conn);
        log.debug("3.param:{}", param);

        try {
            pstmt = conn.prepareStatement(sb.toString());
            log.debug("4.pstmt:{}", pstmt);

            pstmt.setInt(1, param.getFavSeq());

            flag = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
            log.debug("5.finally conn:{} pstmt:{}", conn, pstmt);
        }
        log.debug("6.flag:{}", flag);

        return flag;
    }

    
    @Override
    public List<FavoriteDTO> doRetrieve(DTO search) {
        SearchDTO searchVO = (SearchDTO) search;
        List<FavoriteDTO> list = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        StringBuilder sbWhere = new StringBuilder();

        // 검색 조건 추가
        if (searchVO.getSearchDiv() != null && !searchVO.getSearchDiv().isEmpty()) {
            String searchDiv = searchVO.getSearchDiv().trim(); // 공백 제거나 추가적인 처리
            
            switch (searchDiv) {
                case "book_name":
                    sbWhere.append("WHERE b.book_name LIKE '%' || ? || '%' \n");
                    break;
                case "book_genre":
                    sbWhere.append("WHERE b.book_genre = ? \n");
                    break;
                case "author":
                    sbWhere.append("WHERE b.author LIKE '%' || ? || '%' \n");
                    break;
                default:
                    log.debug("잘못된 조건입니다: {}", searchDiv);
                    break;
            }
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connectionmaker.getConnection();

            // SQL 쿼리 구성
            sb.append("SELECT f.FAV_SEQ, b.BOOK_NAME, b.BOOK_GENRE, b.AUTHOR \n");
            sb.append("FROM favorite f \n");
            sb.append("JOIN book b ON f.book_code = b.book_code \n");
            sb.append(sbWhere.toString());
            sb.append("ORDER BY f.FAV_SEQ DESC \n");

            pstmt = conn.prepareStatement(sb.toString());

            // 파라미터 설정
            int idx = 1;
            if (searchVO.getSearchDiv() != null && !searchVO.getSearchDiv().isEmpty() && 
                searchVO.getSearchWord() != null && !searchVO.getSearchWord().isEmpty()) {
                pstmt.setString(idx++, "%" + searchVO.getSearchWord() + "%");
            }

            rs = pstmt.executeQuery();

            // 결과 처리
            while (rs.next()) {
                FavoriteDTO outVO = new FavoriteDTO();
                outVO.setFavSeq(rs.getInt("FAV_SEQ"));
                outVO.setBookName(rs.getString("BOOK_NAME"));
                outVO.setBookGenre(rs.getString("BOOK_GENRE"));
                outVO.setAuthor(rs.getString("AUTHOR"));

                list.add(outVO);
            }
        } catch (SQLException e) {
            log.error("SQL Exception occurred while retrieving favorites: {}", e.getMessage());
            // 예외를 다시 던져서 상위 레이어에서 처리하도록 함
            throw new RuntimeException("Failed to retrieve favorites", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }

        return list;
    }

    
    
    
    @Override
    public FavoriteDTO doSelectOne(FavoriteDTO param) {
        FavoriteDTO outVO = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        	Connection conn1 = connectionmaker.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT book_title, genre, author \n");
            sb.append("   FROM favorite_books        \n");
            sb.append("  WHERE 1=1                   \n");

            // 검색 조건 추가
            if (param.getBookName() != null && !param.getBookName().isEmpty()) {
                sb.append("    AND book_title = ?       \n");
            }
            if (param.getBookGenre() != null && !param.getBookGenre().isEmpty()) {
                sb.append("    AND genre = ?             \n");
            }
            if (param.getAuthor() != null && !param.getAuthor().isEmpty()) {
                sb.append("    AND author = ?            \n");
            }

            pstmt = conn1.prepareStatement(sb.toString());

            // 파라미터 설정
            int idx = 1;
            if (param.getBookName() != null && !param.getBookName().isEmpty()) {
                pstmt.setString(idx++, param.getBookName());
            }
            if (param.getBookGenre() != null && !param.getBookGenre().isEmpty()) {
                pstmt.setString(idx++, param.getBookGenre());
            }
            if (param.getAuthor() != null && !param.getAuthor().isEmpty()) {
                pstmt.setString(idx++, param.getAuthor());
            }

            rs = pstmt.executeQuery();
            if (rs.next()) {
                outVO = new FavoriteDTO();
                outVO.setBookName(rs.getString("book_title"));
                outVO.setBookGenre(rs.getString("genre"));
                outVO.setAuthor(rs.getString("author"));
            }
        } catch (SQLException e) {
            log.debug("SQLException:" + e.getMessage());
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
	public int doUpdate(FavoriteDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

	




	@Override
	public int doSave(FavoriteDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}

}
