package org.fangsoft.testcenter.command;

import java.util.List;

public final class DisplayAllTestNamesCommand  extends Command {
	private static final long serialVersionUID = 5182294756376719285L;
	private List<String> testNames;
	public DisplayAllTestNamesCommand(){};
	public List<String> getTestNames() {
		return testNames;
	}
	public void setTestNames(List<String> testNames) {
		this.testNames = testNames;
	}
	@Override
	public void execute() {
		if (this.getController() == null) {
			return;
		}
		this.testNames = this.getController().findAllTestNames();
	}
}
