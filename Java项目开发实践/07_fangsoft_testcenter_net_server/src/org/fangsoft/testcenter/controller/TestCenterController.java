package org.fangsoft.testcenter.controller;

import java.util.List;

import org.fangsoft.testcenter.dao.DaoFactory;
import org.fangsoft.testcenter.model.Customer;
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
		return null;
	}
	@Override
	public List<String> findAllTestNames() {
		return null;
	}
	@Override
	public Test selectTest(String testName) {
		return null;
	}
	@Override
	public TestResult startTest(Test test, Customer customer) {
		return null;
	}
	@Override
	public void commitTest(TestResult testResult) {
	}
}
