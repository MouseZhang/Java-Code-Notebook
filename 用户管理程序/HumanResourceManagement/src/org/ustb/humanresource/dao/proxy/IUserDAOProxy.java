package org.ustb.humanresource.dao.proxy;

import java.sql.SQLException;
import java.util.List;

import org.ustb.humanresource.dao.IUserDAO;
import org.ustb.humanresource.dao.impl.IUserDAOImpl;
import org.ustb.humanresource.dbc.DataBaseConnection;
import org.ustb.humanresource.vo.User;


public class IUserDAOProxy implements IUserDAO {
	private DataBaseConnection dbc = null;
	private IUserDAO dao = null;
	
	public IUserDAOProxy() {
		this.dbc = new DataBaseConnection();
		this.dao = new IUserDAOImpl(this.dbc.getConnection());
	}

	@Override
	public boolean doCreate(User user) throws Exception {
		boolean flag = true;
		try {
			flag = this.dao.doCreate(user);
		}catch (SQLException e) {
			throw e;
		}finally {//不管如何抛出，最终都进行数据库的关闭操作
			this.dbc.close();
		}
		return flag;
	}

	@Override
	public boolean doUpdate(User user) throws Exception {
		boolean flag = true;
		try {	
			flag = this.dao.doUpdate(user);
		}catch (SQLException e) {
			throw e;
		}finally {//不管如何抛出，最终都进行数据库的关闭操作
			this.dbc.close();
		}
		return flag;
	}

	@Override
	public boolean doDelete(int id) throws Exception {
		boolean flag = true;
		try {
			flag = this.dao.doDelete(id);
		}catch (SQLException e) {
			throw e;
		}finally {//不管如何抛出，最终都进行数据库的关闭操作
			this.dbc.close();
		}
		return flag;
	}

	@Override
	public User findById(int id) throws Exception {
		User user = null;
		try {
			user = this.dao.findById(id);
		}catch (SQLException e) {
			throw e;
		}finally {//不管如何抛出，最终都进行数据库的关闭操作
			this.dbc.close();
		}
		return user;
	}

	@Override
	public List<User> findAll(String keyWord) throws Exception {
		List<User> all = null;
		try {
			all = this.dao.findAll(keyWord);
		}catch (SQLException e) {
			throw e;
		}finally {//不管如何抛出，最终都进行数据库的关闭操作
			this.dbc.close();
		}
		return all;
	}
}
