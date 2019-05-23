package org.fangsoft.testcenter.data;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.model.Customer;
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
	//main方法
	public static void main(String[] args) {
		exportTest2File();
		exportCustomer2File();
		System.out.println("导入完毕！");
	}
}
