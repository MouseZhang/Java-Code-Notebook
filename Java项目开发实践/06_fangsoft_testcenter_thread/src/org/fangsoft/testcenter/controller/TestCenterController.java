package org.fangsoft.testcenter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fangsoft.testcenter.dao.DaoFactory;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;

public class TestCenterController implements ITestCenterController {
	private DaoFactory daoFactory;
	private Customer customer;
	private TestResult testResult;
	private long testDeadTime;//考试结束时间
	private int index;//试题出现序号
	public TestCenterController() {}
	public DaoFactory getDaoFactory() {
		return daoFactory;
	}
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public TestResult getTestResult() {
		return testResult;
	}
	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}
	public long getTestDeadTime() {
		return testDeadTime;
	}
	public void setTestDeadTime(long testDeadTime) {
		this.testDeadTime = testDeadTime;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	};
	private Test loadQuestion(Test test) {
		this.getDaoFactory().getQuestionDao().loadQuestion(test);
		return test;
	}
	//判断考试时间到
	public boolean isTestTimeout() {
		return (System.currentTimeMillis() - this.getTestDeadTime() > 0);
	}
	public void handleTimeOut() {
		
	}
	@Override
	public Customer login(String userId, String password) {
		return this.getDaoFactory().getCustomerDao().login(userId, password);
	}
	@Override
	public List<String> findAllTestNames() {
		return this.getDaoFactory().getTestDao().findAllTestNames();
	}
	@Override
	public Test selectTest(String testName) {
		return this.getDaoFactory().getTestDao().findTestByName(testName);
	}
	@Override
	public TestResult startTest(Test test, Customer customer) {
		Test loadedTest = this.loadQuestion(test);
		TestResult testResult = new TestResult();
		testResult.setCustomer(customer);
		testResult.setCustomerId(customer.getUserId());
		testResult.setTestId(loadedTest.getId());
		testResult.setTest(loadedTest);
		testResult.setStartTime(new Date());
		long testDeadTime = testResult.getStartTime().getTime() + loadedTest.getTimeLimitMin() * 60 * 1000;
		testResult.setTestDeadTime(testDeadTime);
		this.testDeadTime = testDeadTime;
		this.setTestDeadTime(testDeadTime);
		testResult.setStatus(TestResult.Status.TESTING);
		testResult.setQuestionResult(
			new ArrayList<QuestionResult>(loadedTest.getQuestion().size()));
		for (Question q: loadedTest.getQuestion()) {
			QuestionResult qr = new QuestionResult();
			qr.setQuestion(q);
			testResult.getQuestionResult().add(qr);
		}
		return testResult;
	}
	@Override
	public void commitTest(TestResult testResult) {
		testResult.setEndTime(new Date());
		testResult.commitTest();
		this.getDaoFactory().getTestResultDao().save(testResult);
	}
}
