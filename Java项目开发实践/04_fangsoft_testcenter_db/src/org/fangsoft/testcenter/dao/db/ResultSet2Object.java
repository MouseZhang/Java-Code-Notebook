package org.fangsoft.testcenter.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.util.DataConverter;


public class ResultSet2Object {
	public static Test rs2Test(ResultSet rs) throws SQLException {
		Test t = new Test();
		t.setId(rs.getLong("TT_ID"));
		t.setName(rs.getString("TT_NAME"));
		t.setNumQuestion(rs.getInt("TT_NUMQUESTION"));
		t.setTimeLimitMin(rs.getInt("TT_TIMELIMITMIN"));
		t.setDescription(rs.getString("TT_DESCRIPTION"));
		t.setScore(rs.getInt("TT_SCORE"));
		return t;
	}
	public static Question rs2Question(ResultSet rs) throws SQLException {
		Question q = new Question();
		q.setId(rs.getLong("QN_ID"));
		q.setName(rs.getString("QN_NAME"));
		q.setScore(rs.getInt("QN_SCORE"));
		return q;
	}
	public static ChoiceItem rs2ChoiceItem(ResultSet rs) throws SQLException {
		ChoiceItem item = new ChoiceItem();
		item.setName(rs.getString("CM_NAME"));
		item.setCorrect(DataConverter.int2Boolean(rs.getInt("CM_CORRECT")));
		item.setId(rs.getLong("CM_ID"));
		return item;
	}
	public static Customer rs2Customer(ResultSet rs) throws SQLException {
		Customer c = new Customer();
		c.setId(rs.getLong("CR_ID"));
		c.setUserId(rs.getString("CR_USERID"));
		c.setPassword(rs.getString("CR_PASSWORD"));
		c.setEmail(rs.getString("CR_EMAIL"));
		return c;
	}
	public static TestResult rs2TestResult(ResultSet rs) throws SQLException {
		TestResult tr = new TestResult();
		tr.setId(rs.getLong("TR_ID"));
		tr.setCustomerId(rs.getString("TR_CUSTOMERID"));
		tr.setScore(rs.getInt("TR_SCORE"));
		tr.setResult(Filed2Property.int2TestResult(rs.getInt("TR_RESULT")));
		tr.setStartTime((java.util.Date)rs.getTimestamp("TR_STARTTIME"));
		tr.setEndTime((java.util.Date)rs.getTimestamp("TR_ENDTIME"));
		tr.setStatus(Filed2Property.int2TestResults(rs.getInt("TR_STATUE")));
		tr.setTestId(rs.getLong("TR_TEST_ID"));
		return tr;
	}
	public static QuestionResult rs2QuestionResult(ResultSet rs) throws SQLException {
		QuestionResult qr = new QuestionResult();
		qr.setId(rs.getLong("QR_ID"));
		qr.setScore(rs.getInt("QR_SCORE"));
		qr.setAnswer(rs.getString("QR_ANSWER"));
		qr.setResult(DataConverter.int2Boolean(rs.getInt("QR_RESULT")));
		return qr;
	}
}
