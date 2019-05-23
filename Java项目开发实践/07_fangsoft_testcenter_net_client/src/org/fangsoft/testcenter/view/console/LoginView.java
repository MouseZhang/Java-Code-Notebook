package org.fangsoft.testcenter.view.console;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.view.TestCenterView;
import org.fangsoft.util.Console;

public class LoginView extends ConsoleView implements TestCenterView {
	private String userId;
	private String password;
	private boolean error=false;
	private int loginCount=0;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public int getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	public LoginView() {
		
	}
	@Override
	protected void displayView() {
		if (!error) {
			Console.output("进入fangsoft考试中心前请先登录，输入完成后按enter确认");
		}
		else {
			Console.output("用户名或密码错误，不能登录，请重新登录。" + 
				"注意：登录" + Configuration.MAX_LOGIN + "次不成功，" + 
				"系统将退出，这是第" + (++this.loginCount) + "次");
		}
		this.userId = Console.prompt("输入用户名称：");
		this.password = Console.prompt("输入用户密码：");
	}
}
