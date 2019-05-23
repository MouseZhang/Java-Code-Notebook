package org.fangsoft.db;

public class DBConfig {
	public static final String MYSQL_DRIVER = "org.gjt.mm.mysql.Driver";
	public static final String MYSQL_USER = "root";
	public static final String MYSQL_PASSWORD = "root123";
	public static final String MYSQL_HOST = "localhost";
	public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/testcenter";
	public static String getMySQLDriver() {
		return MYSQL_DRIVER;
	}
	public static String getMySQLUser() {
		return MYSQL_USER;
	}
	public static String getMySQLPassword() {
		return MYSQL_PASSWORD;
	}
	public static final String getMySQLUrl() {
		return MYSQL_URL;
	}
}
