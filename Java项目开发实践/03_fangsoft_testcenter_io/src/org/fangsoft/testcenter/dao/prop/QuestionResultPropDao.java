package org.fangsoft.testcenter.dao.prop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.fangsoft.testcenter.dao.DaoIOConfig;
import org.fangsoft.testcenter.dao.QuestionResultDao;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.TestResult;

public class QuestionResultPropDao implements QuestionResultDao{
	private static final QuestionResultPropDao qrdao = new QuestionResultPropDao();
	public static final QuestionResultPropDao getInstance() {
		return qrdao;
	}
	private QuestionResultPropDao(){};
	public void addQuestionResultToTest(TestResult tr, QuestionResult qr) {
		String trPath = DaoIOConfig.getQuestionResultFilePath(tr);
		new File(trPath).mkdirs();
		String fileName = trPath + DaoIOConfig.getFileName(qr);
		Properties ps = Property2Object.toProperties(qr);
		try {
			ps.store(new FileWriter(fileName), "");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean loadQuestionResult(TestResult tr) {
		return false;
	}
}
