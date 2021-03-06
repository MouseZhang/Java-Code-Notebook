package org.fangsoft.testcenter.config;

import java.text.DateFormat;
import java.util.Locale;

import javax.sql.DataSource;

import org.fangsoft.db.DriverManagerDataSource;
import org.fangsoft.testcenter.dao.DaoFactory;
import org.fangsoft.testcenter.dao.db.DBDaoFactory;

public class Configuration {
	private static final DateFormat dateFormat = 
			DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	public static final DateFormat getDateFormat(Locale...locales) {
		if (locales == null || locales.length == 0) {
			return dateFormat;
		}
		return DateFormat.getDateTimeInstance(
				DateFormat.FULL, DateFormat.FULL, locales[0]);
	}
	public static final int MAX_LOGIN = 3;
	public static final String[] CHOICE_LABEL = {"a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k"};
	private static final DataSource mySQLDataSource = 
			new DriverManagerDataSource(DBConfig.getMySQLDriver(), 
										DBConfig.getMySQLUser(),
										DBConfig.getMySQLPassword(),
										DBConfig.getMySQLUrl());
	public static final DataSource getDataSource() {
		return mySQLDataSource;
	}
	private static DaoFactory daoFactory;
	public static final DaoFactory getDaoFactory() {
		if (daoFactory == null) {
			daoFactory = new DBDaoFactory(Configuration.getDataSource());
		}
		return daoFactory;
	}
}
