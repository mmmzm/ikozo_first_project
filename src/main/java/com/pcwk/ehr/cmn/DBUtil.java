package com.pcwk.ehr.cmn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	
	// close()
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		if(null !=rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} // if
		if(null !=pstmt) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // if
		if(null !=conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // if	
	} // close() 끝
	
	// close()
	public static void close(Connection conn, PreparedStatement pstmt) {
		if(null !=pstmt) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // if
		if(null !=conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // if	
	} // close() 끝
	
	
} // class
