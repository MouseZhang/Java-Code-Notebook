package org.fangsoft.testcenter.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.data.TestData;
import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class QuestionDaoImpl implements QuestionDao {
	private static final QuestionDao qdao = new QuestionDaoImpl();
	public static final QuestionDao getInstance() {
		return qdao;
	}
	private QuestionDaoImpl(){};
	public void loadQuestion(Test test) {
		int numQuestion = test.getNumQuestion();
		String testName = test.getName();
		for (String[][] data: TestData.ALL_TEST) {
			if (data[0][0].equals(testName)) {
				int qi = 0;
				while (qi < numQuestion) {
					String[] qds = data[qi+1];
					Question q = new Question();
					q.setName(qds[0]);
					List<ChoiceItem> items = new ArrayList<ChoiceItem>(qds.length-1);
					for (int j = 1; j < qds.length; j++) {
						ChoiceItem ch = new ChoiceItem();
						String choiceText = qds[j];
						if (choiceText.charAt(0) == '#') {
							choiceText = choiceText.substring(1);
							ch.setCorrect(true);
						}
						ch.setName(choiceText);
						items.add(ch);
					}
					q.setChoiceItem(items);
					q.setScore(1);
					test.addQuestion(q);
					qi++;
				}
			}
		}
	}
}
