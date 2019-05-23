package org.fangsoft.testcenter.model;

public class Test {
	private String name;
	private String description;
	private int numQuestion;
	private int timeLimitMin;
	private int score;
	private Question[] question;
	public Test(int numQuestion) {
		this.numQuestion = numQuestion;
		question = new Question[numQuestion];
	}
	public Test() {
		this(5);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getNumQuestion() {
		return numQuestion;
	}
	public void setNumQuestion(int numQuestion) {
		this.numQuestion = numQuestion;
	}
	public int getTimeLimitMin() {
		return timeLimitMin;
	}
	public void setTimeLimitMin(int timeLimitMin) {
		this.timeLimitMin = timeLimitMin;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Question[] getQuestion() {
		return question;
	}
	public void setQuestion(Question[] question) {
		this.question = question;
	}
	private int count = 0;
	public void addQuestion(Question q) {
		question[count++] = q;
	}
	public Question getQuestion(int index) {
		return question[index];
	}
}
