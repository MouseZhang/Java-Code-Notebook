package org.fangsoft.testcenter.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.fangsoft.db.IRS2Object;
import org.fangsoft.db.SQLAction;
import org.fangsoft.db.SQLUtil;
import org.fangsoft.testcenter.dao.QuestionResultDao;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.util.DataConverter;

public class QuestionResultDBDao extends SQLAction implements QuestionResultDao {
	
	private static final String SQL_SAVE = "INSERT INTO QUESTIONRESULT(QR_ID, QR_SCORE, QR_ANSWER, "
		+ "QR_RESULT, QR_TESTRESULT_ID, QR_QUESTION_ID) VALUES(?,?,?,?,?,?) ";
	
	public QuestionResultDBDao(DataSource ds) {
		super(ds);
	}
	private static final ResultSet2QuestionResult rs2QuestionResult = new ResultSet2QuestionResult();
	public static final ResultSet2QuestionResult getRS2QuestionResult() {
		return rs2QuestionResult;
	}
	private static class ResultSet2QuestionResult implements IRS2Object<QuestionResult> {
		public QuestionResult process(ResultSet rs) throws SQLException {
			return ResultSet2Object.rs2QuestionResult(rs);
		}
	}
	private Object[] questionResult2SQLParameter(QuestionResult qr, long qrId) {
		Object[] p = new Object[6];
		p[0] = qrId;
		p[1] = qr.getScore();
		p[2] = qr.getAnswer();
		p[3] = DataConverter.booolean2Int(qr.isResult());
		p[4] = qr.getTestResultId();
		p[5] = qr.getQuestion().getId();
		return p;
	}
	@Override
	public void save(QuestionResult questionResult) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getDataSource().getConnection();
			long qrId = System.currentTimeMillis();
			questionResult.setId(qrId);
			Object[] objects = this.questionResult2SQLParameter(questionResult, qrId);
			pstmt = conn.prepareStatement(SQL_SAVE);
			pstmt.setLong(1, (long)objects[0]);
			pstmt.setInt(2, (int)objects[1]);
			pstmt.setString(3, (String)objects[2]);
			pstmt.setInt(4, (int)objects[3]);
			pstmt.setLong(5, (long)objects[4]);
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
