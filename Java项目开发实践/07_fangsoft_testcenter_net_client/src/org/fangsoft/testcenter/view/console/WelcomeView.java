package org.fangsoft.testcenter.view.console;

import java.util.Date;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.view.TestCenterView;
import org.fangsoft.util.Console;

public class WelcomeView extends ConsoleView implements TestCenterView {
	@Override
	protected void displayView() { 
		Console.output("今天是：" + Configuration.getDateFormat().format(new Date()));
		Console.output("你的操作系统是：" + System.getProperty("os.name"));
	}
}
