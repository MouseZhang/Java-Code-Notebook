package org.fangsoft.testcenter;

import org.fangsoft.net.client.NetClient;
import org.fangsoft.testcenter.command.ICommand;
import org.fangsoft.testcenter.config.SocketConfig;
import org.fangsoft.testcenter.controller.console.ConsoleTestCenterController;
import org.fangsoft.testcenter.view.console.WelcomeView;

public class TestCenterByClient {
	private static NetClient<ICommand, ICommand> getNetClient() {
		NetClient<ICommand, ICommand> netClient = new NetClient<ICommand, ICommand>();
		netClient.setServerHost(SocketConfig.getServerHost());
		netClient.setServerPort(SocketConfig.getServerPort());
		netClient.setTimeout(SocketConfig.getSocketTimeout());
		netClient.setProxyServer(SocketConfig.getProxyServer());
		netClient.setProxyPort(SocketConfig.getProxyPort());
		netClient.setProxyType(SocketConfig.getProxyType());
		return netClient;
	}
	public static void runMVC() {
		ConsoleTestCenterController tcc = new ConsoleTestCenterController();
		tcc.setNetClient(getNetClient());
		WelcomeView view = new WelcomeView();
		view.addObserver(tcc);
		view.display();
	}
	public static void main(String[] args) {
		runMVC();
	}
}
