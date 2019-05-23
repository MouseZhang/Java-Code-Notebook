package org.fangsoft.testcenter.model;
import java.util.Date;
import java.util.List;

public class TestResult {
	private static final int UNKNOW_SCORE = -1;
	private long id;
	private Customer customer;
	private String customerId;
	private Test test;
	private long testDeadTime;
	public long getTestId() {
		return testId;
	}
	public void setTestId(long testId) {
		this.testId = testId;
	}
	private long testId;
	private int score = UNKNOW_SCORE;	//新建，考试得分未知
	private Result result;
	private List<QuestionResult> questionResult;
	private Date startTime;
	private Date endTime;
	private Status status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public static enum Status {
		NEW(0, "new"),
		TESTING(1, "testing"),
		SUSPEND(2, "suspend"),
		FINISH(3, "finish");
		private int intVal;
		private String description;
		private Status(int intVal, String desc) {
			this.intVal = intVal;
			this.description = desc;
		}
		public int getIntVal() {
			return this.intVal;
		}
		public String getDescription() {
			return this.description;
		}
	}
	public static enum Result {
		PASS(1, "pass"),
		FAILURE(0, "fail"),
		UNKNOW(-1, "unknow");
		private String value;
		private int intVal;
		private Result(int intVal, String value) {
			this.value = value;
			this.intVal = intVal;
		}
		public String getValue() {
			return this.value;
		}
		public int getIntVal() {
			return this.intVal;
		}
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Test getTest() {
		return test;
	}
	public void setTest(Test test) {
		this.test = test;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public List<QuestionResult> getQuestionResult() {
		return questionResult;
	}
	public QuestionResult getQuestionResult(int index) {
		return questionResult.get(index);
	}
	public void setQuestionResult(List<QuestionResult> questionResult) {
		this.questionResult = questionResult;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public long getTestDeadTime() {
		return testDeadTime;
	}
	public void setTestDeadTime(long testDeadTime) {
		this.testDeadTime = testDeadTime;
	}
	protected int computeScore() {
		if (this.getStatus() == Status.FINISH) {
			return this.score;
		}
		if (this.score < 0) {
			this.score = 0;
		}
		for (QuestionResult qr: this.questionResult) {
			this.score += qr.computeAnswer();
		}
		return this.score;
	}
	protected Result computePass() {
		int rights = 0;
		for (QuestionResult qr: this.questionResult) {
			if (qr.isResult()) {
				rights++;
			}
		}
		if (100 * rights >= 70 * this.getQuestionResult().size()) {
			this.setResult(Result.PASS);
		}
		else {
			this.setResult(Result.FAILURE);
		}
		return this.getResult();
	}
	public Result commitTest() {
		if (this.status == Status.FINISH) {
			return this.getResult();
		}
		else if (this.getStatus() == Status.TESTING) {
			this.computeScore();
			this.computePass();
			this.setStatus(Status.FINISH);
			return this.getResult();
		}
		this.setResult(Result.UNKNOW);
		return Result.UNKNOW;
	}
	public void suspendTest() {
		if (this.status != Status.TESTING) {
			return;
		}
		this.setEndTime(new Date());
		this.setStatus(Status.SUSPEND);
	}
	public void continueTest() {
		if (this.status != Status.SUSPEND) {
			return;
		}
		long elapsedTime = this.getEndTime().getTime() - this.getStartTime().getTime();
		long testTime = this.getTest().getTimeLimitMin() - elapsedTime / (60 * 1000);
		this.getTest().setTimeLimitMin((int)testTime);
		this.setStatus(Status.TESTING);
	}
}
