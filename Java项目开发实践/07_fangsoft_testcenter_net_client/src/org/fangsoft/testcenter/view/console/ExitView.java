package org.fangsoft.testcenter.view.console;

import org.fangsoft.testcenter.view.TestCenterView;
import org.fangsoft.util.Console;

public class ExitView extends ConsoleView implements TestCenterView {
	private String mesg;
	public ExitView() {};
	public ExitView(String mesg) {
		this.mesg = mesg;
	}
	public String getMesg() {
		return mesg;
	}
	public void setMesg(String mesg) {
		this.mesg = mesg;
	}
	@Override
	protected void displayView() {
		Console.output(mesg);
	}
}
