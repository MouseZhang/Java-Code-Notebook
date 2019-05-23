package org.fangsoft.testcenter.dao;

import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.TestResult;

public interface QuestionResultDao {
	public void addQuestionResultToTest(TestResult tr, QuestionResult qr);
	public boolean loadQuestionResult(TestResult tr);
}
