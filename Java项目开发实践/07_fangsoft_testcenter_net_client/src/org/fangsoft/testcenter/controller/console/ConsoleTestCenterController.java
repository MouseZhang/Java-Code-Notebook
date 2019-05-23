package org.fangsoft.testcenter.controller.console;

import java.util.Observable;
import java.util.Observer;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.controller.NetTestCenterController;
import org.fangsoft.testcenter.controller.TestTimeoutGuard;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.view.console.EndTestView;
import org.fangsoft.testcenter.view.console.ExitView;
import org.fangsoft.testcenter.view.console.IsTakeTestView;
import org.fangsoft.testcenter.view.console.LoginView;
import org.fangsoft.testcenter.view.console.ModifyAnswerView;
import org.fangsoft.testcenter.view.console.PresentQuestionView;
import org.fangsoft.testcenter.view.console.PromptEndTestView;
import org.fangsoft.testcenter.view.console.SelectTestView;
import org.fangsoft.testcenter.view.console.StartTestView;
import org.fangsoft.testcenter.view.console.WelcomeView;

public class ConsoleTestCenterController extends NetTestCenterController implements Observer {
	private TestTimeoutGuard testTimeoutGuard;
	
	public TestTimeoutGuard getTestTimeoutGuard() {
		return testTimeoutGuard;
	}
	public void setTestTimeoutGuard(TestTimeoutGuard testTimeoutGuard) {
		this.testTimeoutGuard = testTimeoutGuard;
	}
	@Override
	public void update(Observable observable, Object obj) {
		if (observable instanceof WelcomeView) {
			this.handle((WelcomeView)observable);
		}
		else if (observable instanceof LoginView) {
			this.handle((LoginView)observable);
		}
		else if (observable instanceof IsTakeTestView) {
			this.handle((IsTakeTestView)observable);
		}
		else if (observable instanceof SelectTestView) {
			this.handle((SelectTestView)observable);
		}
		else if (observable instanceof StartTestView) {
			this.handle((StartTestView)observable);
		}
		else if (observable instanceof PresentQuestionView) {
			this.handle((PresentQuestionView)observable);
		}
		else if (observable instanceof PromptEndTestView) {
			this.handle((PromptEndTestView)observable);
		}
		else if (observable instanceof ModifyAnswerView) {
			this.handle((ModifyAnswerView)observable);
		}
		else if (observable instanceof EndTestView) {
			this.handle((EndTestView)observable);
		}
		else if (observable instanceof ExitView) {
			this.handle((ExitView)observable);
		}
	}
	private void handle(WelcomeView view) {
		view.deleteObservers();
		LoginView loginView = new LoginView();
		loginView.addObserver(this);
		loginView.display();
	}
	private void handle(LoginView view) {
		String userId = view.getUserId();
		String password = view.getPassword();
		this.setCustomer(this.login(userId, password));
		if (this.getCustomer() != null) {
			IsTakeTestView tView = new IsTakeTestView();
			tView.addObserver(this);
			tView.display();
			view.deleteObservers();
		}
		else {
			if (view.getLoginCount() < Configuration.MAX_LOGIN) {
				view.setError(true);
				view.display();
			}
			else {
				view.deleteObservers();
				new ExitView("登录错误，系统退出").display();
			}
		}
	}
	private void handle(IsTakeTestView view) {
		view.deleteObservers();
		if (view.isTakeTest()) {
			SelectTestView stView = new SelectTestView();
			stView.setTestNames(findAllTestNames());
			stView.addObserver(this);
			stView.display();
		}
		else {
			new ExitView("用户已取消考试").display();
		}
	}
	private void handle(SelectTestView view) {
		view.deleteObservers();
		StartTestView startView = new StartTestView(this.selectTest(view.getTestName()));
		startView.addObserver(this);
		startView.display();
	}
	private void handle(StartTestView view) {
		view.deleteObservers();
		Test test = view.getTest();
		this.setTestResult(this.startTest(test, this.getCustomer()));
		this.setIndex(1);
		testTimeoutGuard = new TestTimeoutGuard(this);
		QuestionResult qr = this.getTestResult().getQuestionResult(this.getIndex() - 1);
		PresentQuestionView pqView = new PresentQuestionView(qr, this.getIndex(), Configuration.CHOICE_LABEL);
		pqView.addObserver(this);
		pqView.display();
 	}
	private void handle(PresentQuestionView view) {
		if (this.getIndex() < this.getTestResult().getTest().getNumQuestion()) {
			this.setIndex(this.getIndex() + 1);
			view.setSequence(this.getIndex());
			view.setQuestionResult(this.getTestResult().getQuestionResult(this.getIndex() - 1));
			view.display();
		}
		else {
			view.deleteObservers();
			promptEndTest();
		}
	}
	private void handle(PromptEndTestView view) {
		String read = view.getRead();
		if (read.equals("end")) {
			view.deleteObservers();
			displayEndTestView(isTestTimeout());
		}
		else {
			view.deleteObservers();
			int theQuestionIndex = Integer.parseInt(view.getRead()) - 1;
			ModifyAnswerView modifyAnswerView = new ModifyAnswerView(view.getTr().getQuestionResult(theQuestionIndex), theQuestionIndex + 1);
			modifyAnswerView.addObserver(this);
			modifyAnswerView.display();
			promptEndTest();
		}
	}
	private void handle(ModifyAnswerView view) {
		view.deleteObservers();
		promptEndTest();
	}
	private void handle(EndTestView view) {
		if (!this.isTestTimeout()) {
			this.testTimeoutGuard.setTestFinished(true);
		}
		view.deleteObservers();
		ExitView exitView = new ExitView("考试结束，系统退出");
		exitView.addObserver(this);
		exitView.display();
	}
	private void handle(ExitView view) {
		System.exit(0);
	}
	private void promptEndTest() {
		PromptEndTestView view = new PromptEndTestView(this.getTestResult().getTest().getNumQuestion());
		view.setTr(this.getTestResult());
		view.addObserver(this);
		view.display();
	}
	//考试结束显示内容：时间到或考生自己提交
	private synchronized void displayEndTestView(boolean timeOut) {
		//提交考试
		commitTest(getTestResult());
		//显示考试报告
		EndTestView endTestView = new EndTestView(this.getTestResult(), timeOut);
		endTestView.addObserver(this);
		endTestView.display();
	}
	public void handleTimeOut() {
		displayEndTestView(true);
	}
}
