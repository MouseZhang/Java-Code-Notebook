package org.fangsoft.testcenter.command;

import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;

public final class StartTestCommand extends Command {
	private static final long serialVersionUID = -494645455279773240L;
	private Test test;
	private Customer customer;
	private TestResult testResult;
	public StartTestCommand(){};
	public StartTestCommand(Test test, Customer customer) {
		this.test = test;
		this.customer = customer;
	}
	public Test getTest() {
		return test;
	}
	public void setTest(Test test) {
		this.test = test;
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
	@Override
	public void execute() {
		if (this.getController() == null) {
			return;
		}
		this.testResult = this.getController().startTest(this.getTest(), this.getCustomer());
	}
}
