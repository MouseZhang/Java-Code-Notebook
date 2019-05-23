package org.fangsoft.testcenter.command;

import org.fangsoft.testcenter.model.Test;

public final class SelectTestCommand extends Command {
	private static final long serialVersionUID = -1767516720336500790L;
	private String testName;
	private Test test;
	public SelectTestCommand(){};
	public SelectTestCommand(String testName) {
		this.testName = testName;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public Test getTest() {
		return test;
	}
	public void setTest(Test test) {
		this.test = test;
	}
	@Override
	public void execute() {
		if (this.getController() == null) {
			return;
		}
		this.setTest(this.getController().selectTest(this.getTestName()));
	}
}
