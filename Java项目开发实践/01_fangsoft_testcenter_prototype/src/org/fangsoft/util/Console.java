package org.fangsoft.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.IOException;

public class Console {
	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private static PrintStream out = System.out;
	
	public static String read() {
		try {
			return input.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	public static void output(Object msg) {
		out.println(msg);
	}
	public static void output(String format, Object...msgs) {//格式化输出
		out.printf(format, msgs);
	}
	//对话框功能
	public static String prompt(Object q, Object...options) {
		output(q);
		if (options != null) {
			if (options.length == 1) {
				output(options[0]);
			}
			else {
				for (int i = 0; i < options.length; i++) {
					output("%1$s. %2$s%n", i+1, options[i]);
				}
			}
		}
		return read();
	}
	//提示回答是否的方法
	public static boolean promptYesNo(Object q, String yes, Object...options) {
		String answer = prompt(q, options);
		if (answer != null && answer.equalsIgnoreCase(yes)) {
			return true;
		}
		return false;
	}
}
