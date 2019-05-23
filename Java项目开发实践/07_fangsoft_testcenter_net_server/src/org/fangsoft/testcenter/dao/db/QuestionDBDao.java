package org.fangsoft.testcenter.dao.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.fangsoft.db.IRS2Object;
import org.fangsoft.db.SQLAction;
import org.fangsoft.db.SQLUtil;
import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.util.DataConverter;
import org.fangsoft.util.DataValidator;
import org.fangsoft.util.NumberUtil;


public class QuestionDBDao extends SQLAction implements QuestionDao {
	
	public static final String SQL_SAVE = "INSERT INTO QUESTION(QN_NAME, QN_ANSWER, QN_SCORE, "
			+ "QN_TEST_ID, QN_ID) VALUES(?,?,?,?,?) ";
	public static final String SQL_CHOICEITEM_SAVE = "INSERT INTO CHOICEITEM (CM_NAME, "
			+ "CM_CORRECT, CM_QUESTION_ID, CM_ID) VALUES(?,?,?,?) ";
	public static final String SQL_ADDCHOICEITEMTOQUESTION = "SELECT * FROM CHOICEITEM "
			+ "WHERE CM_QUESTION_ID = ? ";
	public static final String SQL_LOADQUESTION = "SELECT * FROM QUESTION WHERE QN_TEST_ID = ? ";
	public static final String SQL_LOADQUESTION_COUNT = "SELECT COUNT(*) FROM QUESTION WHERE QN_TEST_ID = ? ";
	
	public QuestionDBDao(DataSource ds) {
		super(ds);
	}
	private static final ResultSet2ChoiceItem rs2Item = new ResultSet2ChoiceItem();
	public static final ResultSet2ChoiceItem getRS2Item() {
		return rs2Item;
	}
	private static class ResultSet2ChoiceItem implements IRS2Object<ChoiceItem> {
		public ChoiceItem process(ResultSet rs) throws SQLException {
			return ResultSet2Object.rs2ChoiceItem(rs);
		}
	}
	public static Question rs2Question(ResultSet rs) throws SQLException {
		return ResultSet2Object.rs2Question(rs);
	}
	protected Object[] question2SQLParam(Question q, long testId) {
		Object[] p = new Object[5];
		p[0] = DataValidator.validate(q.getName());
		p[1] = q.getAnswer();
		p[2] = q.getScore();
		p[3] = testId;
		p[4] = q.getId();
		return p;
	}
	protected Object[] choiceItem2SQLParam(ChoiceItem c, long qid) {
		Object[] p = new Object[4];
		p[0] = DataValidator.validate(c.getName());
		p[1] = DataConverter.booolean2Int(c.isCorrect());
		p[2] = qid;
		p[3] = c.getId();
		return p;
	}
	private void addChoiceItemToQuestion(Question q, Connection conn) throws SQLException {
		List<ChoiceItem> items = this.query2List(getRS2Item(), SQL_ADDCHOICEITEMTOQUESTION, q.getId());
		q.setChoiceItem(items);
	}
	private void saveChoiceItem(Connection conn, Question q, long qid) throws SQLException {
		List<ChoiceItem> items = q.getChoiceItem();
		PreparedStatement pstmt = null;
		if (items != null && items.size() > 0) {
			for (ChoiceItem c: items) {
				long cid = System.currentTimeMillis();
				c.setId(cid);
				Object[] objects = this.choiceItem2SQLParam(c, qid);
				pstmt = conn.prepareStatement(SQL_CHOICEITEM_SAVE);
				pstmt.setString(1, (String)objects[0]);
				pstmt.setInt(2, (int)objects[1]);
				pstmt.setLong(3, (long)objects[2]);
				pstmt.setLong(4, (long)objects[3]);
				pstmt.executeUpdate();
			}
			pstmt.close();
		}
	}
	@Override
	public void addQuestionToTest(Test test, Question q) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getDataSource().getConnection();
			long questionId = System.currentTimeMillis();
			q.setId(questionId);
			Object[] objects = this.question2SQLParam(q, test.getId());
			pstmt = conn.prepareStatement(SQL_SAVE);
			pstmt.setString(1, (String)objects[0]);
			pstmt.setString(2, (String)objects[1]);
			pstmt.setInt(3, (int)objects[2]);
			pstmt.setLong(4, (long)objects[3]);
			pstmt.setLong(5, (long)objects[4]);
			pstmt.executeUpdate();
			pstmt.close();
			this.saveChoiceItem(conn, q, q.getId());
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			SQLUtil.close(conn);
		}
	}

	@Override
	public boolean loadQuestion(Test test) {
		long tid = test.getId();
		if (tid == 0) {
			return false;
		}
		Connection conn = null;
		ResultSet qrs = null;
		int count = 0;
		int[] indexes = null;
		int numQuestion = test.getNumQuestion();
		try {
			conn =  this.getDataSource().getConnection();
			conn.setAutoCommit(false);
			ResultSet rs = this.query2ResultSet(conn, SQL_LOADQUESTION_COUNT, tid);
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			if (count < numQuestion) {
				return false;
			}
			indexes = NumberUtil.uniRandomNumbers(0, count, numQuestion);
			PreparedStatement stmt = conn.prepareStatement(SQL_LOADQUESTION, 
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setLong(1, tid);
			qrs = stmt.executeQuery();
			if (!qrs.next()) {
				return false;
			}
			for (int i: indexes) {
				boolean ok = qrs.absolute(i + 1);
				if (ok) {
					Question q = rs2Question(qrs);
					this.addChoiceItemToQuestion(q, conn);
					test.addQuestion(q);
				}
			}
			conn.commit();
			return true;
		}catch (SQLException e) {
			SQLUtil.rollback(conn);
			e.printStackTrace();
		}finally {
			SQLUtil.close(qrs);
			SQLUtil.close(conn);
		}
		return false;
	}
}
