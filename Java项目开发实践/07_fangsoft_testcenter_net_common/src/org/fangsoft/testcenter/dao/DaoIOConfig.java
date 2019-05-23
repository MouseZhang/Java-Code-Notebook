package org.fangsoft.testcenter.dao;

import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;

public abstract class DaoIOConfig {
	
	public static final String SUFFIX = ".fan";
	public static final String TESTCENTER_PATH = "/Users/MouseZhang/Documents/workspace/03_fangsoft_testcenter_io/data/fangsoft/";
	public static final String TESTRESULT_PATH = "testresult/";
	public static final String TEST_PATH = "test/";
	public static final String CUSTOMER_PATH = "customer/";
	
	public static String getBasePath() {
		return TESTCENTER_PATH;
	}
	public static String getTestFilePath() {
		return getBasePath() + TEST_PATH;
	}
	public static String getFileName(Test test) {
		return test.getName() + SUFFIX;
	}
	public static String getQuestionFilePath(Test test) {
		return getTestFilePath() + test.getName() + "/";
	}
	public static String getFileName(Question question) {
		return question.getClass().getSimpleName() + "-" +
			   question.getName().hashCode() + SUFFIX;
	}
	public static String getChoiceItemFilePath(Test test, Question question) {
		return getQuestionFilePath(test) + "/" + question.getClass().getSimpleName() + "-" +
	           question.getName().hashCode() + "/";
	}
	public static String getFileName(ChoiceItem item) {
		return item.getClass().getSimpleName() + "-" + item.getName().hashCode() + SUFFIX;
	}
	public static String getCustomerFilePath() {
		return getBasePath() + CUSTOMER_PATH;
	}
	public static String getFileName(Customer customer) {
		return customer.getUserId() + SUFFIX;
	}
	public static String getTestResultFilePath(String userId) {
		return getBasePath() + TESTRESULT_PATH + userId + "/";
	}
	public static String getFileName(TestResult testResult) {
		return testResult.getClass().getSimpleName() + "-" + 
			   testResult.getStartTime() + SUFFIX;
	}
	public static String getQuestionResultFilePath(TestResult testResult) {
		return getTestResultFilePath(testResult.getCustomer().getUserId()) + "/" + testResult.getClass().getSimpleName() + "-" +
			   testResult.getStartTime() + "/";
	}
	public static String getFileName(QuestionResult questionResult) {
		return questionResult.getClass().getSimpleName() + "-" +
				questionResult.getQuestion().getName().hashCode() + SUFFIX;
	}
}
