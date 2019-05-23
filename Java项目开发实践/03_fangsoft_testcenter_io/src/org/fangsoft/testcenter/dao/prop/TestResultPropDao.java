package org.fangsoft.testcenter.dao.prop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fangsoft.testcenter.dao.DaoIOConfig;
import org.fangsoft.testcenter.dao.TestResultDao;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.TestResult;

public class TestResultPropDao implements TestResultDao {
	private static final TestResultPropDao trdao = new TestResultPropDao();
	public static final TestResultPropDao getInstance() {
		return trdao;
	}
	private TestResultPropDao(){};
	public List<TestResult> findTestResultByCustomer(String userId) {
		Customer customer = new Customer();
		customer.setUserId(userId);
		String path = DaoIOConfig.getTestResultFilePath(userId);
		File[] files = new File(path).listFiles();
		if (files == null || files.length == 0) {
			return new ArrayList<TestResult>(0);
		}
		ArrayList<TestResult> testResults = new ArrayList<TestResult>(files.length);
		Properties ps = new Properties();
		try {
			for (File f: files) {
				if (f.isDirectory()) {
					continue;
				}
				ps.load(new FileReader(f));
				testResults.add(Property2Object.toTestResult(ps));
				ps.clear();
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		testResults.trimToSize();
		return testResults;
	}
	public void save(TestResult testResult) {
		Properties ps = Property2Object.toProperties(testResult);
		String path = DaoIOConfig.getTestResultFilePath(testResult.getCustomer().getUserId());
		String fileName = DaoIOConfig.getFileName(testResult);
		new File(path).mkdirs();
		try {
			ps.store(new FileWriter(path + fileName), "");
		}catch (IOException e) {
			e.printStackTrace();
		}
		List<QuestionResult> qrList = testResult.getQuestionResult();
		if (qrList != null) {
			for (QuestionResult qr: qrList) {
				QuestionResultPropDao.getInstance().addQuestionResultToTest(testResult, qr);
			}
		}
	}
}
