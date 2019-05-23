package org.fangsoft.testcenter.view.console;

import java.util.Date;
import java.util.List;

import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.view.TestCenterView;
import org.fangsoft.util.Console;

public class ModifyAnswerView extends ConsoleView implements TestCenterView {
	public static final String[] CHOICE_LABEL = {"a", "b", "c", "d", "e", "f", 
		"g", "h", "i", "j", "k"};
	private QuestionResult qr;
	private int sequence;
	private String[] labels;
	public ModifyAnswerView(QuestionResult qr, int sequence) {
		this.qr = qr;
		this.sequence = sequence;
	}
	public QuestionResult getQr() {
		return qr;
	}
	public void setQr(QuestionResult qr) {
		this.qr = qr;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String[] getLabels() {
		return labels;
	}
	public void setLabels(String[] labels) {
		this.labels = labels;
	}
	public static String[] getChoiceLabel() {
		return CHOICE_LABEL;
	}
	@Override
	protected void displayView() {
		Console.output("原来的答案：" + this.qr.getAnswer());
		Console.output("现在的时间是：%1$tT%n", new Date());
		if (this.labels == null) {
			this.labels = CHOICE_LABEL;
		}
		this.qr.getQuestion().assignLabel(this.labels);
		String answer = prompt(this.sequence, this.qr.getQuestion());
		this.qr.setAnswer(answer);
		this.qr.computeAnswer();
	}
	public static String prompt(int pos, Question q) {
		Console.output("%1$s.%2$s%n", pos, q.getName());
		List<ChoiceItem> items = q.getChoiceItem();
		for (ChoiceItem item: items) {
			Console.output("%1$s.%2$s%n", item.getLabel(), item.getName());
		}
		Console.output("输入答案：");
		return Console.read();
	}
}
