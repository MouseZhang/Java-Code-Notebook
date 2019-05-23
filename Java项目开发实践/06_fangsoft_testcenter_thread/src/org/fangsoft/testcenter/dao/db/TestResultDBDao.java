package org.fangsoft.testcenter.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.fangsoft.db.IRS2Object;
import org.fangsoft.db.SQLAction;
import org.fangsoft.db.SQLUtil;
import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.dao.TestResultDao;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.util.DataConverter;

public class TestResultDBDao extends SQLAction implements TestResultDao {

	private static final String SQL_SAVE = "INSERT INTO TESTRESULT(TR_CUSTOMERID, TR_SCORE, "
		+ "TR_RESULT, TR_STARTTIME, TR_ENDTIME, TR_STATUE, TR_TEST_ID, TR_ID) VALUES(?,?,?,?,?,?,?,?) ";
	private static final String SQL_FINDRESULTBYCUSTOMER = "SELECT * FROM TESTRESULT WHERE TR_CUSTOMERID = ? ";
	
	public TestResultDBDao(DataSource ds) {
		super(ds);
	}
	private static final ResultSet2TestResult rs2TestResult = new ResultSet2TestResult();
	public static final ResultSet2TestResult getRS2TestResult() {
		return rs2TestResult;
	}
	private static class ResultSet2TestResult implements IRS2Object<TestResult> {
		public TestResult process(ResultSet rs) throws SQLException {
			return ResultSet2Object.rs2TestResult(rs);
		}
	}
	private Object[] testResult2SQLParameter(TestResult tr, long trId) {
		Object[] p = new Object[8];
		p[0] = tr.getScore();
		p[1] = Filed2Property.testResult2Int(tr.getResult());
		p[2] = DataConverter.date2SqlTimestamp(tr.getStartTime());
		p[3] = DataConverter.date2SqlTimestamp(tr.getEndTime());
		p[4] = Filed2Property.testResults2Int(tr.getStatus());
		p[5] = tr.getTest().getId();
		p[6] = trId;
		return p;
	}
	@Override
	public List<TestResult> findTestResultByCustomer(String userId) {
		try {
			return this.query2List(getRS2TestResult(), SQL_FINDRESULTBYCUSTOMER, userId);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void save(TestResult testResult) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getDataSource().getConnection();
			long trId = System.currentTimeMillis();
			testResult.setId(trId);
			Object[] objects = this.testResult2SQLParameter(testResult, trId);
			pstmt = conn.prepareStatement(SQL_SAVE);
			CustomerDao cdao = Configuration.getDaoFactory().getCustomerDao();
			Customer customer = cdao.findByUserId(testResult.getCustomer().getUserId());
			pstmt.setLong(1, customer.getId());
			pstmt.setInt(2, (int)objects[0]);
			pstmt.setInt(3, (int)objects[1]);
			pstmt.setTimestamp(4, (Timestamp)objects[2]);
			pstmt.setTimestamp(5, (Timestamp)objects[3]);
			pstmt.setInt(6, (int)objects[4]);
			pstmt.setLong(7, (long)objects[5]);
			pstmt.setLong(8, (long)objects[6]);
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
