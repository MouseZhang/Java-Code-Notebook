package org.fangsoft.testcenter.dao.prop;

import org.fangsoft.testcenter.dao.TestResultDao;
import org.fangsoft.testcenter.dao.TestResultDaoTest;

public class TestResultPropsDaoTest extends TestResultDaoTest {
	public void test() {
		TestResultDao trdao = TestResultPropDao.getInstance();
		this.testAll(trdao);
	}
}
