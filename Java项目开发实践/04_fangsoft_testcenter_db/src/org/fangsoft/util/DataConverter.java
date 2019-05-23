package org.fangsoft.util;

import java.util.Date;


import org.fangsoft.testcenter.config.Configuration;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DataConverter {
	public static Date str2Date(String str) {
		try {
			return Configuration.getDateFormat().parse(str);
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	public static int str2Int(String str) {
		if (str == null) {
			return 0;
		}
		try {
			return Integer.parseInt(str);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static String date2Str(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHmmss");
		String str = sdf.format(date);
		return str;
	}
	public static java.sql.Date date2SqlDate(java.util.Date date) {
		if (date == null) {
			return new java.sql.Date(System.currentTimeMillis());
		}
		else if (date instanceof java.sql.Date) {
			return (java.sql.Date)date;
		}
		return new java.sql.Date(date.getTime());
	}
	public static Timestamp date2SqlTimestamp(java.util.Date date) {
		if (date == null) {
			return new Timestamp(System.currentTimeMillis());
		}
		return new Timestamp(date.getTime());
	}
	public static boolean int2Boolean(int intVal) {
		if (intVal == 1) {
			return true;
		}
		return false;
	}
	public static int booolean2Int(boolean b) {
		if (b) {
			return 1;
		}
		return 0;
	}
}
