package org.fangsoft.testcenter.dao.db;

import javax.sql.DataSource;

import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.dao.DaoFactory;
import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.dao.QuestionResultDao;
import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.dao.TestResultDao;


public class DBDaoFactory implements DaoFactory{
	private DataSource dataSource;
	private CustomerDao cdao;
	private TestDao tdao;
	private QuestionDao qdao;
	private TestResultDao trdao;
	private QuestionResultDao qrdao;
	public DBDaoFactory(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public CustomerDao getCustomerDao() {
		if (this.cdao == null) {
			this.cdao = new CustomerDBDao(this.dataSource);
		}
		return this.cdao;
	}
	public TestDao getTestDao() {
		if (this.tdao == null) {
			this.tdao = new TestDBDao(this.dataSource);
		}
		return this.tdao;
	}
	public QuestionDao getQuestionDao() {
		if (this.qdao == null) {
			this.qdao = new QuestionDBDao(this.dataSource);
		}
		return this.qdao;
	}
	public TestResultDao getTestResultDao() {
		if (this.trdao == null) {
			this.trdao = new TestResultDBDao(this.dataSource);
		}
		return this.trdao;
	}
	public QuestionResultDao getQuestionResultDao() {
		if (this.qrdao == null) {
			this.qrdao = new QuestionResultDBDao(this.dataSource);
		}
		return this.qrdao;
	}
}
