package org.fangsoft.testcenter.model;

public class Question {
	private String name;
	private String answer;
	private int score;
	private ChoiceItem[] choiceItem;
	private int count;
	public Question() {}
	public Question(int size) {
		choiceItem =  new ChoiceItem[size];
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public ChoiceItem[] getChoiceItem() {
		return choiceItem;
	}
	public void setChoiceItem(ChoiceItem[] choiceItem) {
		this.choiceItem = choiceItem;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void addChoiceItem(ChoiceItem item) {
		choiceItem[count++] = item;
	}
	public ChoiceItem getChoiceItem(int index) {
		return choiceItem[index];
	}
	public void assignLabel(String[] label) {
		int count = 0;
		StringBuffer rightAnswer = new StringBuffer();
		if (this.choiceItem != null) {
			for (ChoiceItem item: this.getChoiceItem()) {
				item.setLabel(label[count++]);
				if (item.isCorrect()) {
					rightAnswer.append(label[count - 1]);
				}
			}
		}
		this.setAnswer(rightAnswer.toString());
	}
}
