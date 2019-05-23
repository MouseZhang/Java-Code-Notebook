package org.fangsoft.testcenter.command;

import org.fangsoft.testcenter.controller.ITestCenterController;

public class Command implements ICommand {
	private static final long serialVersionUID = -6492370344988597824L;
	protected transient ITestCenterController controller;
	public Command(){};
	public Command(ITestCenterController controller) {
		this.controller = controller;
	}
	public ITestCenterController getController() {
		return controller;
	}
	public void setController(ITestCenterController controller) {
		this.controller = controller;
	}
	@Override
	public void execute() {
	}
}
