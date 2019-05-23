package org.fangsoft.testcenter.dao.db;

import org.fangsoft.testcenter.model.TestResult;


public class Filed2Property {
	public static TestResult.Status int2TestResults(int intVal) {
		for (TestResult.Status tt: TestResult.Status.values()) {
			if (intVal == tt.getIntVal()) {
				return tt;
			}
		}
		return TestResult.Status.FINISH;
	}
	public static int testResults2Int(TestResult.Status trStatus) {
		if (trStatus == null) {
			return TestResult.Status.FINISH.getIntVal();
		}
		return trStatus.getIntVal();
	}
	public static TestResult.Result int2TestResult(int intVal) {
		for (TestResult.Result st: TestResult.Result.values()) {
			if (intVal == st.getIntVal()) {
				return st;
			}
		}
		return TestResult.Result.UNKNOW;
	}
	public static int testResult2Int(TestResult.Result testStatus) {
		if (testStatus == null) {
			return TestResult.Result.UNKNOW.getIntVal();
		}
		return testStatus.getIntVal();
	}
}
