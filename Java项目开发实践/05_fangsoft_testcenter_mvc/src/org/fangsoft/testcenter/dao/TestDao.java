package org.fangsoft.testcenter.dao;

import java.util.List;

import org.fangsoft.testcenter.model.Test;

public interface TestDao {
	public Test findTestByName(String testName);
	public List<Test> findAllTest();
	public List<String> findAllTestNames();
	public void save(Test test);
}
