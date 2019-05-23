package org.fangsoft.testcenter.data;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class Setup {
	//将考试数据导入到文件
	public static void exportTest2File() {
		for (String[][] data: TestData.ALL_TEST) {
			Test test = TestData.newTest(data);
			TestData.loadQuestion(test, data);
			Configuration.getDaoFactory().getTestDao().save(test);
		}
	}
	//将考生数据导入到文件
	public static void exportCustomer2File() {
		for (Customer c: CustomerData.getCustomer()) {
			Configuration.getDaoFactory().getCustomerDao().save(c);
		}
	}
	//将考试数据导入到数据库
	public static void exportTest2Database() throws Exception {
		TestDao tdao = Configuration.getDaoFactory().getTestDao();
		QuestionDao qdao = Configuration.getDaoFactory().getQuestionDao();
		for (String[][] data: TestData.ALL_TEST) {
			Test test = TestData.newTest(data);
			TestData.loadQuestion(test, data);
			tdao.save(test);
			for (Question q: test.getQuestion()) {
				qdao.addQuestionToTest(test, q);
			}
		}
	}
	//将考生数据导入到数据库
	public static void exportCustomer2Database() throws Exception {
		CustomerDao cdao = Configuration.getDaoFactory().getCustomerDao();
		for (Customer c: CustomerData.getCustomer()) {
			cdao.save(c);
		}
	}
	//main方法
	public static void main(String[] args) throws Exception {
		exportTest2Database();
		exportCustomer2Database();
		System.out.println("导入完毕！");
	}
}
