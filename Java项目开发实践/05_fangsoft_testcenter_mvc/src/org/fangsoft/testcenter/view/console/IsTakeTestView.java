package org.fangsoft.testcenter.view.console;

import org.fangsoft.util.Console;

public class IsTakeTestView extends ConsoleView {
	private boolean isTakeTest;
	public boolean isTakeTest() {
		return isTakeTest;
	}
	@Override
	protected void displayView() {
		this.isTakeTest = Console.promptYesNo("确认参加考试吗？","y","是：y", "否，退出：n");
	}
}
