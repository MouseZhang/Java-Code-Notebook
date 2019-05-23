package org.fangsoft.testcenter.view.console;

import java.util.Observable;

import org.fangsoft.testcenter.view.TestCenterView;
import org.fangsoft.util.Console;

public abstract class ConsoleView extends Observable implements TestCenterView {
	@Override
	public final void display() {
		Console.output("=========== fangsoft ===========");
		displayView();
		Console.output("== copyright Fangsoft Inc, all rights reserved. ==");
		Console.output("");
		setChanged();
		notifyObservers();
	}
	protected abstract void displayView();
}
