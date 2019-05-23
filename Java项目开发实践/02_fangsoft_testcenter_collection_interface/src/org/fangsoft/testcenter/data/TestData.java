package org.fangsoft.testcenter.data;


import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class TestData {
	private static final String RIGHT_CHOICE="#";
	private static final String[][] JAVA_QUESTION_LIB = {
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
	private static final String[][] WEB_QUESTION_LIB = {
			//test属性：name, numQuestion, timeLimitMin, description, score
			{
				"Web知识测试",
				"3",
				"10",
				"www.fangsoft.org的web知识测试",
				"10"
			},
			{
				//Question属性：name
				"有关Web语言论述正确是？",
					//ChoiceItem
					"#它应用了Http协议",
					"#常用的web服务器为Apache",
					"#Web动态应用常用Jsp开发",
					"#Http协议有1.1版"
			},
			{
				"Web学习常可以参考的网站有？",
					"#java.sun.com",
					"#www.javaworld.com",
					"#www-130.ibm.com/developerworks/",
					"#www.fangsoft.org"
			},
			{
				"J2ee中的Web开发技术有？",
					"#Jsp",
					"#Servlet",
					"#JSF",
					"#Custom tags"
			}
	};
	public static final String[][][] ALL_TEST = {
			JAVA_QUESTION_LIB,
			WEB_QUESTION_LIB
	};
	//创建并初始化Test对象
	public static Test newTest(String[][] data) {
		Test test = new Test();
		String[] tds = data[0];
		int numQuestion = Integer.parseInt(tds[1]);
		int numQ = data.length;
		if (numQuestion > (numQ - 1)) {
			numQuestion = numQ - 1;
		}
		test.setName(tds[0]);
		test.setNumQuestion(numQuestion);
		test.setTimeLimitMin(Integer.parseInt(tds[2]));
		test.setDescription(tds[3]);
		test.setScore(Integer.parseInt(tds[4]));
		return test;
	}
	//为考试装载试题
	public static void loadQuestion(Test test, String[][] data) {
		int qi = 0;
		while (qi < data.length - 1) {
			String[] qds = data[qi+1];
			Question q = new Question();
			q.setName(qds[0]);
			for (int j = 1; j < qds.length; j++) {
				ChoiceItem ch = new ChoiceItem();
				String choiceText = qds[j];
				if (choiceText.startsWith(RIGHT_CHOICE)) {
					choiceText = choiceText.substring(1);
					ch.setCorrect(true);
				}
				ch.setName(choiceText);
				q.addChoiceItem(ch);
			}
			q.setScore(1);
			test.addQuestion(q);
			qi++;
		}
	}
}
