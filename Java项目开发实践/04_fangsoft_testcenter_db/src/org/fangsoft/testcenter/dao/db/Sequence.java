package org.fangsoft.testcenter.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.fangsoft.db.SQLUtil;

public class Sequence {
	
	public static final String SEQ_TEST = "SEQ_TEST";
	public static final String SEQ_QUESTION = "SEQ_QUESTION";
	public static final String SEQ_CHOICEITEM = "SEQ_CHOICEITEM";
	public static final String SEQ_TESTRESULT = "SEQ_QUESTIONRESULT";
	public static final String SEQ_QUESTIONRESULT = "SEQ_QUESTIONRESULT";
	
	public static final int getSeqValue(Connection conn, String seqName) throws SQLException {
		ResultSet rs = null;
		String sql = "SELECT " + seqName + ".NEXTVAL FROM DUAL";
		try {
			rs = conn.prepareStatement(sql).executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}catch (SQLException e) {
			throw e;
		}finally {
			SQLUtil.close(rs);
		}
		return -1;
	}
}
