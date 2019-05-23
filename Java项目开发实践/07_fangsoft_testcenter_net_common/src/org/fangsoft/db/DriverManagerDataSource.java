package org.fangsoft.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;


public class DriverManagerDataSource implements DataSource{
	private String user;
	private String password;
	private String url;
	private String driver;
	public DriverManagerDataSource(String driver, String user, String password, String url) {
		this.driver = driver;
		this.user = user;
		this.password = password;
		this.url = url;
		try {
			Class.forName(this.driver);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(this.url, this.user, this.password);
	}
	public Connection getConnection(String userName, String password) throws SQLException{
		return DriverManager.getConnection(this.url, userName, password);
	}
	public boolean isWrapperFor(java.lang.Class<?> iface) throws SQLException {
		return false;
	}
	public <T> T unwrap(java.lang.Class<T> iface) throws SQLException {
		return null;
	}
	public java.io.PrintWriter getLogWriter() throws SQLException {
		return null;
	}
	public void setLogWriter(java.io.PrintWriter out) throws SQLException {
		
	}
	public void setLoginTimeout(int seconds) throws SQLException {
		
	}
	public int getLoginTimeout() throws SQLException {
		return 0;
	}
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
}
