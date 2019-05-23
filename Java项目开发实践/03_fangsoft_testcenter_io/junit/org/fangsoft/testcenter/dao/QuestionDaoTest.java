package org.fangsoft.testcenter.dao;

import java.util.ArrayList;

import org.fangsoft.testcenter.data.TestData;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;
import org.junit.Assert;

import junit.framework.TestCase;

public class QuestionDaoTest extends TestCase {
	protected void testAll(QuestionDao qdao) {
		ArrayList<Test> tests = new ArrayList<Test>(TestData.ALL_TEST.length);
		for (String[][] data : TestData.ALL_TEST) {
			Test test = TestData.newTest(data);
			tests.add(test);
		}
		tests.trimToSize();
		for (Test test : tests) {
			for (Question q : test.getQuestion()) {
				qdao.addQuestionToTest(test, q);
			}
		}
		for (Test test : tests) {
			qdao.loadQuestion(test);
			Assert.assertEquals(3, test.getQuestion().size());
		}
	}
}
