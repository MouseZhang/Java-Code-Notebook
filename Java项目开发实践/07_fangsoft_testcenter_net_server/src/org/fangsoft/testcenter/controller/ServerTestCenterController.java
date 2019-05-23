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

public class ServerTestCenterController extends TestCenterController {
	private DaoFactory daoFactory;
	public ServerTestCenterController(){};
	public DaoFactory getDaoFactory() {
		return daoFactory;
	}
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	public boolean isTestTimeout() {
		return (System.currentTimeMillis() - this.getTestDeadTime() > 0);
	}
	public void handleTimeOut() {
		
	}
	@Override
	public Customer login(String userId, String password) {
		return daoFactory.getCustomerDao().login(userId, password);
	}
	@Override
	public List<String> findAllTestNames() {
		return this.getDaoFactory().getTestDao().findAllTestNames();
	}
	@Override
	public Test selectTest(String testName) {
		return this.getDaoFactory().getTestDao().findTestByName(testName);
	}
	public Test loadQuestion(Test test) {
		this.getDaoFactory().getQuestionDao().loadQuestion(test);
		return test;
	}
	@Override
	public TestResult startTest(Test test, Customer customer) {
		Test loadedTest = this.loadQuestion(test);
		TestResult testResult = new TestResult();
		testResult.setCustomer(customer);
		testResult.setTest(loadedTest);
		testResult.setCustomerId(customer.getUserId());
		testResult.setTestId(loadedTest.getId());
		testResult.setStartTime(new Date());
		long testDeadTime = testResult.getStartTime().getTime() + loadedTest.getTimeLimitMin() * 1000 * 60;
		this.setTestDeadTime(testDeadTime);
		testResult.setStatus(TestResult.Status.TESTING);
		testResult.setQuestionResult(new ArrayList<QuestionResult>(loadedTest.getQuestion().size()));
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
		List<QuestionResult> qrLists = testResult.getQuestionResult();
		for (QuestionResult qr: qrLists) {
			qr.setTestResultId(testResult.getId());
			this.getDaoFactory().getQuestionResultDao().save(qr);;	
		}
	}
}
