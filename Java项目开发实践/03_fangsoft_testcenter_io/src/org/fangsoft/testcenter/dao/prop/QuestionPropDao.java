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
import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.dao.SuffixFilter;
import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.util.NumberUtil;

public class QuestionPropDao implements QuestionDao {
	private static final QuestionPropDao qdao = new QuestionPropDao();
	public static final QuestionPropDao getInstance() {
		return qdao;
	}
	private QuestionPropDao(){};
	public void addQuestionToTest(Test test, Question question) {
		String path = DaoIOConfig.getQuestionFilePath(test);
		new File(path).mkdirs();
		String fileName = path + DaoIOConfig.getFileName(question);
		Properties ps = Property2Object.toProperties(question);
		try {
			ps.store(new FileWriter(fileName), "");
		}catch (IOException e) {
			e.printStackTrace();
		}
		List<ChoiceItem> items = question.getChoiceItem();
		if (items != null && items.size() > 0) {
			String cpath = DaoIOConfig.getChoiceItemFilePath(test, question);
			new File(cpath).mkdirs();
			try {
				for (ChoiceItem item: items) {
					Properties cps = Property2Object.toProperties(item);
					String cfileName = DaoIOConfig.getFileName(item);
					cps.store(new FileWriter(cpath + cfileName), "");
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean loadQuestion(Test test) {
		int numQuestion = test.getNumQuestion();
		String path = DaoIOConfig.getQuestionFilePath(test);
		File[] files = new File(path).listFiles(new SuffixFilter(DaoIOConfig.SUFFIX));
		if (files == null || files.length == 0) {
			return false;
		}
		int qSize = files.length;
		//random sequence
		int[] indexs = null;
		if (qSize < numQuestion) {
			return false;
		}
		else {
			indexs = NumberUtil.uniRandomNumbers(0, qSize, numQuestion);
		}
		Properties ps = new Properties();
		try {
			for (int i = 0; i < numQuestion; i++) {
				ps.load(new FileReader(files[indexs[i]]));
				Question q = Property2Object.toQuestion(ps);
				q.setChoiceItem(this.loadChoiceItem(test, q));
				test.addQuestion(q);
				ps.clear();
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	private List<ChoiceItem> loadChoiceItem(Test t, Question q) {
		String path = DaoIOConfig.getChoiceItemFilePath(t, q);
		File[] files = new File(path).listFiles(new SuffixFilter(DaoIOConfig.SUFFIX));
		if (files != null && files.length > 0) {
			List<ChoiceItem> items = new ArrayList<ChoiceItem>(files.length);
			Properties ps = new Properties();
			for (File f: files) {
				try {
					ps.load(new FileReader(f));
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				}
				items.add(Property2Object.toChoiceItem(ps));
				ps.clear();
			}
			return items;
		}
		return new ArrayList<ChoiceItem>(0);
	}
}
