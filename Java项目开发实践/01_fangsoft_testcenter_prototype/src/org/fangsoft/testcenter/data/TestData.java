package org.fangsoft.testcenter.data;

import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class TestData {
	private static final String RIGHT_CHOICE="#";
	private static final String[][] TEST_QUESTION_LIB = {
			//test属性：name, numQuestion, timeLimitMin, description, score
			{
				"Java知识测试",
				"3",
				"10",
				"www.fangsoft.org的java知识测试",
				"10"
			},
			{
				//Question属性：name
				"有关Java语言论述正确是？",
					//ChoiceItem
					"#它是一门编程语言",
					"#它是一个平台",
					"#它是跨平台的",
					"#它是面向对象的"
			},
			{
				"Java学习常可以参考的网站有？",
					"#java.sun.com",
					"#www.javaworld.com",
					"#www-130.ibm.com/developerworks/",
					"#www.fangsoft.org"
			},
			{
				"如果一个属性用private声明，下面论述正确的是？",
					"#不可变",
					"#同步(synchronized)",
					"#封装",
					"#代表is-a关系"
			}
	};
	public static Test produceTest() {
		String[] tds = TEST_QUESTION_LIB[0];
		int numQ  = TEST_QUESTION_LIB.length;
		int numQuestion = Integer.parseInt(tds[1]);
		if (numQuestion > (numQ - 1)) {
			numQuestion = numQ - 1;
		}
		Test test = new Test(numQuestion);
		test.setName(tds[0]);
		test.setNumQuestion(numQuestion);
		test.setTimeLimitMin(Integer.parseInt(tds[2]));
		test.setDescription(tds[3]);
		test.setScore(Integer.parseInt(tds[4]));
		int qi = 0;
		while (qi < numQuestion) {
			String[] qds = TEST_QUESTION_LIB[qi+1];
			Question q = new Question();
			q.setName(qds[0]);
			ChoiceItem[] items = new ChoiceItem[qds.length-1];
			for (int j = 1; j < qds.length; j++) {
				ChoiceItem ch = new ChoiceItem();
				String choiceText = qds[j];
				if (choiceText.indexOf(RIGHT_CHOICE) == 0) {
					choiceText = choiceText.substring(1);
					ch.setCorrect(true);
				}
				ch.setName(choiceText);
				items[j-1] = ch;
			}
			q.setChoiceItem(items);
			q.setScore(1);
			test.addQuestion(q);
			qi++;
		}
		return test;
	}
}
