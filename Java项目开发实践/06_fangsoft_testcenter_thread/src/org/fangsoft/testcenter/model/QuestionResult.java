package org.fangsoft.testcenter.model;

public class QuestionResult {
	@SuppressWarnings("unused")
	private static final int DEFAULT_SCORE = -1;
	private long id;
	private Question question;
	private int score=-1;
	private String answer;
	private boolean result;
	private long testResultId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public long getTestResultId() {
		return testResultId;
	}
	public void setTestResultId(long testResultId) {
		this.testResultId = testResultId;
	}
	public int computeAnswer() {
		if (this.getQuestion() != null 
			&& this.answer != null 
			&& this.answer.length() != 0 
			&& this.getQuestion().getAnswer().equals(this.answer)) { 
			this.result = true;
			this.score = this.getQuestion().getScore();
			if (this.getQuestion().getScore() <= 0) {
				this.score = 1;
			}
		}
		else {
			this.result = false;
			this.score = 0;
		}
		return score;
	}
}
