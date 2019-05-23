package org.fangsoft.util;

import java.util.Date;

import org.fangsoft.testcenter.config.Configuration;

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
}
