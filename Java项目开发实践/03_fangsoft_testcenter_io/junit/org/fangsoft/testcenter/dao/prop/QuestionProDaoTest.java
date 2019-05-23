package org.fangsoft.testcenter.dao.prop;

import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.dao.QuestionDaoTest;

public class QuestionProDaoTest extends QuestionDaoTest {
	public void test() {
		QuestionDao qdao = QuestionPropDao.getInstance();
		this.testAll(qdao);
	}
}
