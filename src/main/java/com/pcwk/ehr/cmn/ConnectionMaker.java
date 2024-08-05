package com.pcwk.ehr.cmn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMaker implements PLog {
	final	static	String	DB_DRIVER	= "oracle.jdbc.driver.OracleDriver";
	// jdbc:oracle:thin:@IP:PORT:전역DB명칭(SID)
	final	static	String	DB_URL 		= "jdbc:oracle:thin:@118.33.104.105:1522:xe";
	final	static	String	DB_USER 	= "ikuzo";
	final	static	String	DB_PASSWORD = "pcwk12";
	
	public ConnectionMaker() {
		log.debug("ConnectionMarker()");
	}
	
	/*
	 * DB연결 정보 생성
	 * @return Connection
	 * */
	public Connection getConnection() {
		Connection conn = null;
		
		log.debug("1");
		try {
			Class.forName(DB_DRIVER);
			log.debug("2");
			
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			log.debug("3 conn : {}", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;		
	}	
} // class
