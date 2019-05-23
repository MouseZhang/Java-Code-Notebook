package org.fangsoft.testcenter.dao;

import java.util.List;

import org.fangsoft.testcenter.model.TestResult;
import org.junit.Assert;

import junit.framework.TestCase;

public class TestResultDaoTest extends TestCase {
	protected void testAll(TestResultDao tdao) {
		String userId = "fangsoft.java@gmail.com";
		List<TestResult> results = tdao.findTestResultByCustomer(userId);
		Assert.assertEquals(results.size(), 1);
	}
}
