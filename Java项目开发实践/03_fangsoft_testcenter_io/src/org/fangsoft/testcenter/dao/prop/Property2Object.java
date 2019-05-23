package org.fangsoft.testcenter.dao.prop;

import java.util.Properties;

import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.util.DataConverter;

public class Property2Object {
	public static Properties toProperties(Test test) {
		Properties ps = new Properties();
		ps.setProperty("class", test.getClass().getName());
		ps.setProperty("name", test.getName());
		ps.setProperty("timeLimitMin", String.valueOf(test.getTimeLimitMin()));
		ps.setProperty("numQuestion", String.valueOf(test.getNumQuestion()));
		ps.setProperty("description", test.getDescription());
		ps.setProperty("score", String.valueOf(test.getScore()));
		return ps;
	}
	public static Test toTest(Properties ps) {
		Test t = null;
		String className = ps.getProperty("class");
		if (className.length() == 0) {
			return null;
		}
		try {
			t = (Test)Class.forName(className).newInstance();
			t.setName(ps.getProperty("name"));
			t.setNumQuestion(DataConverter.str2Int(ps.getProperty("numQuestion")));
			t.setTimeLimitMin(DataConverter.str2Int(ps.getProperty("timeLimitMin")));
			t.setScore(DataConverter.str2Int(ps.getProperty("score")));
			t.setDescription(ps.getProperty("description"));
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return t;
	}
	public static Properties toProperties(Question q) {
		Properties ps = new Properties();
		ps.setProperty("class", q.getClass().getName());
		ps.setProperty("name", q.getName());
		if (q.getAnswer() == null) {
			ps.setProperty("answer", "");
		}else {
			ps.setProperty("answer", q.getAnswer());
		}
		ps.setProperty("score", String.valueOf(q.getScore()));
		return ps;
	}
	public static Question toQuestion(Properties ps) {
		Question q = null;
		String className = ps.getProperty("class");
		if (className.length() == 0) {
			return null;
		}
		try {
			q = (Question)Class.forName(className).newInstance();
			q.setName(ps.getProperty("name"));
			q.setScore(DataConverter.str2Int(ps.getProperty("score")));
			q.setAnswer(ps.getProperty("answer"));
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return q;
	}
	public static Properties toProperties(ChoiceItem item) {
		Properties ps = new Properties();
		ps.setProperty("class", item.getClass().getName());
		ps.setProperty("name", item.getName());
		ps.setProperty("correct", String.valueOf(item.isCorrect()));
		if (item.getLabel() == null) {
			ps.setProperty("label", "");
		} else {
			ps.setProperty("label", item.getLabel());
		}
		return ps;
	}
	public static ChoiceItem toChoiceItem(Properties ps) {
		ChoiceItem item = null;
		String className = ps.getProperty("class");
		if (className.length() == 0) {
			return null;
		}
		try {
			item = (ChoiceItem)Class.forName(className).newInstance();
			item.setName(ps.getProperty("name"));
			item.setCorrect(Boolean.parseBoolean(ps.getProperty("correct")));
			item.setLabel(ps.getProperty("label"));
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return item;
	}
	public static Properties toProperties(Customer customer) {
		Properties ps = new Properties();
		ps.setProperty("class", customer.getClass().getName());
		ps.setProperty("userId", customer.getUserId());
		ps.setProperty("password", customer.getPassword());
		ps.setProperty("email", customer.getEmail());
		return ps;
	}
	public static Customer toCustomer(Properties ps) {
		Customer customer = null;
		String className = ps.getProperty("class");
		if (className.length() == 0) {
			return null;
		}
		try {
			customer = (Customer)Class.forName(className).newInstance();
			customer.setUserId(ps.getProperty("userId"));
			customer.setPassword(ps.getProperty("password"));
			customer.setEmail(ps.getProperty("email"));
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return customer;
	}
	public static Properties toProperties(TestResult testResult) {
		Properties ps = new Properties();
		ps.setProperty("class", testResult.getClass().getName());
		ps.setProperty("score", String.valueOf(testResult.getScore()));
		ps.setProperty("pass", String.valueOf(testResult.getPass()));
		ps.setProperty("startTime", DataConverter.date2Str(testResult.getStartTime()));
		ps.setProperty("endTime", DataConverter.date2Str(testResult.getEndTime()));
		return ps;
	}
	public static TestResult toTestResult(Properties ps) {
		TestResult testResult = null;
		String className = ps.getProperty("class");
		if (className.length() == 0) {
			return null;
		}
		try {
			testResult = (TestResult)Class.forName(className).newInstance();
			testResult.setScore(Integer.parseInt(ps.getProperty("score")));
			testResult.setPass(Integer.parseInt(ps.getProperty("pass")));
			testResult.setStartTime(DataConverter.str2Date(ps.getProperty("startTime")));
			testResult.setEndTime(DataConverter.str2Date(ps.getProperty("endTime")));
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return testResult;
	}
	public static Properties toProperties(QuestionResult questionResult) {
		Properties ps = new Properties();
		ps.setProperty("class", questionResult.getClass().getName());
		ps.setProperty("score", String.valueOf(questionResult.getScore()));
		ps.setProperty("answer", questionResult.getAnswer());
		ps.setProperty("result", String.valueOf(questionResult.isResult()));
		return ps;
	}
	public static QuestionResult toQuestionResult(Properties ps) {
		QuestionResult questionResult = null;
		String className = ps.getProperty("class");
		if (className.length() == 0) {
			return null;
		}
		try {
			questionResult = (QuestionResult)Class.forName(className).newInstance();
			questionResult.setScore(Integer.parseInt(ps.getProperty("score")));
			questionResult.setAnswer(ps.getProperty("answer"));
			questionResult.setResult(Boolean.parseBoolean(ps.getProperty("result")));
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return questionResult;
	}
}
