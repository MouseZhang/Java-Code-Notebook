package org.fangsoft.testcenter.dao.prop;

import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.dao.TestDaoTest;

public class TestPropDaoTest extends TestDaoTest{
	public void test() {
		TestDao tdao = TestPropDao.getInstance();
		this.testAll(tdao);
	}
}
