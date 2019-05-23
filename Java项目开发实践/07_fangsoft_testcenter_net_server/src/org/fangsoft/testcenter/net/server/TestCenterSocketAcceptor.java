package org.fangsoft.testcenter.net.server;

import java.net.Socket;


import org.fangsoft.net.server.ServerTask;
import org.fangsoft.net.server.SocketAcceptor;
import org.fangsoft.testcenter.command.Command;
import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.controller.ServerTestCenterController;
import org.fangsoft.testcenter.controller.TestCenterController;

public class TestCenterSocketAcceptor extends SocketAcceptor<Command, Command> {
	private TestCenterController controller;
	public TestCenterSocketAcceptor(int port) {
		super(port);
	}
	public TestCenterController getController() {
		return controller;
	}
	public void setController(TestCenterController controller) {
		this.controller = controller;
	}
	private TestCenterController getTestCenterController() {
		if (this.controller == null) {
			this.controller = new ServerTestCenterController();
			this.controller.setDaoFactory(Configuration.getDaoFactory());
		}
		return this.controller;
	}
	@Override
	protected ServerTask<Command, Command> generateServerTask(Socket socket) {
		return new TestCenterServerTask(socket, getTestCenterController());
	}
}
