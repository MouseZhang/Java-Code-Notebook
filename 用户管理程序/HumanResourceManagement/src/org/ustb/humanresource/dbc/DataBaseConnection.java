package org.ustb.humanresource.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
	private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/humanresource";
	private static final String DBUSER = "root";
	private static final String DBPASS = "root123";
	private Connection conn = null;
	public DataBaseConnection() {
		try {
			Class.forName(DBDRIVER);
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		return this.conn;
	}
	public void close() {
		if (this.conn != null) {
			try {
				this.conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
