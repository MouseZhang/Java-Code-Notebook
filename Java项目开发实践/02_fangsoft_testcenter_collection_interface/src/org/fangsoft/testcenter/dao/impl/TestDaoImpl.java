package org.fangsoft.testcenter.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.data.TestData;
import org.fangsoft.testcenter.model.Test;

public class TestDaoImpl implements TestDao {
	private static final TestDao tdao = new TestDaoImpl();
	public static final TestDao getInstance() {
		return tdao;
	}
	private TestDaoImpl(){};
	public Test findTestByName(String testName) {
		for (String[][] data: TestData.ALL_TEST) {
			if (data[0][0].equals(testName)) {
				return TestData.newTest(data);
			}
		}
		return null;
	}
	
	public List<Test> findAllTest() {
		ArrayList<Test> tests = new ArrayList<Test>(TestData.ALL_TEST.length);
		for (String[][] data: TestData.ALL_TEST) {
			tests.add(TestData.newTest(data));
		}
		tests.trimToSize();
		return tests;
	}
}
