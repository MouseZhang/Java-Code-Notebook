package org.fangsoft.testcenter.command;

import org.fangsoft.testcenter.model.TestResult;

public final class CommitTestCommand extends Command {
	private static final long serialVersionUID = -5151466428528636287L;
	private TestResult testResult;
	public CommitTestCommand(){};
	public CommitTestCommand(TestResult testResult) {
		this.testResult = testResult;
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
		this.getController().commitTest(this.getTestResult());
	}
}
