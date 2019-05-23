package org.fangsoft.testcenter.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.fangsoft.db.IRS2Object;
import org.fangsoft.db.SQLAction;
import org.fangsoft.db.SQLUtil;
import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.util.DataValidator;


public class TestDBDao extends SQLAction implements TestDao {
	
	public static final String SQL_SAVE = "INSERT INTO TEST(TT_NAME, "
		+ "TT_NUMQUESTION, TT_TIMELIMITMIN, TT_DESCRIPTION, TT_SCORE, TT_ID) VALUES(?,?,?,?,?,?) ";
	public static final String SQL_FINDALLTEST = "SELECT * FROM TEST ";
	public static final String SQL_FINDTESTBYNAME = "SELECT * FROM TEST WHERE TT_NAME = ? ";
	
	public TestDBDao(DataSource ds) {
		super(ds);
	}
	private static final ResultSet2Test rs2Test = new ResultSet2Test();
	public static final ResultSet2Test getRS2Test() {
		return rs2Test;
	}
	private static class ResultSet2Test implements IRS2Object<Test> {
		public Test process(ResultSet rs) throws SQLException {
			return ResultSet2Object.rs2Test(rs);
		}
	}
	private Object[] test2SQLParameter(Test test, long testId) {
		Object[] p = new Object[6];
		p[0] = DataValidator.validate(test.getName());
		p[1] = test.getNumQuestion();
		p[2] = test.getTimeLimitMin();
		p[3] = DataValidator.validate(test.getDescription());
		p[4] = test.getScore();
		p[5] = testId;
		return p;
	}
	@Override
	public Test findTestByName(String testName) {
		try {
			return this.query2Object(getRS2Test(), SQL_FINDTESTBYNAME, testName);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Test> findAllTest() {
		try {
			return this.query2List(getRS2Test(), SQL_FINDALLTEST);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Test>(0);
	}
	@Override
	public void save(Test test) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getDataSource().getConnection();
			long testId = System.currentTimeMillis();
			test.setId(testId);
			Object[] objects = this.test2SQLParameter(test, test.getId());
			pstmt = conn.prepareStatement(SQL_SAVE);
			pstmt.setString(1, (String)objects[0]);
			pstmt.setInt(2, (int)objects[1]);
			pstmt.setInt(3, (int)objects[2]);
			pstmt.setString(4, (String)objects[3]);
			pstmt.setInt(5, (int)objects[4]);
			pstmt.setLong(6, (long)objects[5]);
			pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			SQLUtil.rollback(conn);
			e.printStackTrace();
		}finally {
			SQLUtil.close(conn);
		}
	}
}
