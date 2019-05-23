package org.fangsoft.testcenter.model;

import java.util.ArrayList;
import java.util.List;

public class Test {
	private long id;
	private String name;
	private String description;
	private int numQuestion;
	private int timeLimitMin;
	private int score;
	private List<Question> question;
	public Test(int numQuestion) {
		this.numQuestion = numQuestion;
		question = new ArrayList<Question>(numQuestion);
	}
	public Test() {
		this(5);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public List<Question> getQuestion() {
		return question;
	}
	public void setQuestion(List<Question> question) {
		this.question = question;
	}
	public void addQuestion(Question q) {
		question.add(q);
	}
	public Question getQuestion(int index) {
		return question.get(index);
	}
}
