package org.fangsoft.testcenter.config;

import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {
	private static final int corePoolSize = 5;
	private static final int maxPoolSize = 10;
	private static final int keepAliveTime = 3000;
	private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
	public static int getCorepoolsize() {
		return corePoolSize;
	}
	public static int getMaxpoolsize() {
		return maxPoolSize;
	}
	public static int getKeepalivetime() {
		return keepAliveTime;
	}
	public static TimeUnit getTimeUnit() {
		return TIME_UNIT;
	}
}
