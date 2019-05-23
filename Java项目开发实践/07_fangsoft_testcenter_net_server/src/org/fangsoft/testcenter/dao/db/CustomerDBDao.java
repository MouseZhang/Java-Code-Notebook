package org.fangsoft.testcenter.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.fangsoft.db.IRS2Object;
import org.fangsoft.db.SQLAction;
import org.fangsoft.db.SQLUtil;
import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.util.DataValidator;

public class CustomerDBDao extends SQLAction implements CustomerDao { 
	
	private static final String SQL_SAVE = "INSERT INTO CUSTOMER(CR_USERID, CR_PASSWORD, CR_EMAIL, CR_ID) VALUES(?,?,?,?) ";
	private static final String SQL_FINDBYUSERID = "SELECT * FROM CUSTOMER WHERE CR_USERID = ? ";
	private static final String SQL_LOGIN = "SELECT * FROM CUSTOMER WHERE CR_USERID = ? AND CR_PASSWORD = ? ";
	
	public CustomerDBDao(DataSource ds) {
		super(ds);
	}
	private static final ResultSet2Customer rs2Customer = new ResultSet2Customer();
	public static final ResultSet2Customer getRS2Customer() {
		return rs2Customer;
	}
	private static class ResultSet2Customer implements IRS2Object<Customer> {
		public Customer process(ResultSet rs) throws SQLException {
			return ResultSet2Object.rs2Customer(rs);
		}
	}
	private Object[] customer2SQLParameter(Customer c) {
		Object[] p = new Object[4];
		p[0] = DataValidator.validate(c.getUserId());
		p[1] = c.getPassword();
		p[2] = c.getEmail();
		p[3] = c.getId();
		return p;
	}
	@Override
	public void save(Customer customer) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getDataSource().getConnection();
			long customerId = System.currentTimeMillis();
			customer.setId(customerId);
			Object[] objects = this.customer2SQLParameter(customer);
			pstmt = conn.prepareStatement(SQL_SAVE);
			pstmt.setString(1, (String)objects[0]);
			pstmt.setString(2, (String)objects[1]);
			pstmt.setString(3, (String)objects[2]);
			pstmt.setLong(4, (long)objects[3]);
			pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			SQLUtil.rollback(conn);
			e.printStackTrace();
		}finally {
			SQLUtil.close(conn);
		}
	}
	@Override
	public Customer findByUserId(String userId) {
		try {
			return this.query2Object(getRS2Customer(), SQL_FINDBYUSERID, userId);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Customer login(String userId, String password) {
		try {
			return this.query2Object(getRS2Customer(), SQL_LOGIN, userId, password);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
