package org.ustb.humanresource.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ustb.humanresource.dao.IUserDAO;
import org.ustb.humanresource.vo.User;


public class IUserDAOImpl implements IUserDAO {
	private Connection conn = null;
	
	public IUserDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean doCreate(User user) throws Exception {
		boolean flag = false;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO user(name,sex,birthday) VALUES(?,?,?) ";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getSex());
			pstmt.setDate(3, new java.sql.Date(user.getBirthday().getTime()));
			if (pstmt.executeUpdate() > 0) {
				flag = true;
			}
		}catch (SQLException e) {
			throw e;
		}finally {//不管如何抛出，最终都进行数据库的关闭操作
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
					throw e;
				}
			}
		}
		return flag;
	}

	@Override
	public boolean doUpdate(User user) throws Exception {
		boolean flag = false;
		PreparedStatement pstmt = null;
		String sql = "UPDATE user SET name=?,sex=?,birthday=? WHERE id=? ";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getSex());
			pstmt.setDate(3, new java.sql.Date(user.getBirthday().getTime()));
			pstmt.setInt(4, user.getId());
			if (pstmt.executeUpdate() > 0) {
				flag = true;
			}
		}catch (SQLException e) {
			throw e;
		}finally {//不管如何抛出，最终都进行数据库的关闭操作
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
					throw e;
				}
			}
		}
		return flag;
	}

	@Override
	public boolean doDelete(int id) throws Exception {
		boolean flag = false;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM user WHERE id=? ";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			if (pstmt.executeUpdate() > 0) {
				flag = true;
			}
		}catch (SQLException e) {
			throw e;
		}finally {//不管如何抛出，最终都进行数据库的关闭操作
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
					throw e;
				}
			}
		}
		return flag;
	}

	@Override
	public User findById(int id) throws Exception {
		User user = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT id,name,sex,birthday FROM user WHERE id=? ";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();//执行查询操作
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setSex(rs.getString(3));
				user.setBirthday(rs.getDate(4));
			}
			rs.close();
		}catch (SQLException e) {
			throw e;
		}finally {//不管如何抛出，最终都进行数据库的关闭操作
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
					throw e;
				}
			}
		}
		return user;
	}

	@Override
	public List<User> findAll(String keyWord) throws Exception {
		List<User> all = new ArrayList<User>();
		PreparedStatement pstmt = null;
		String sql = "SELECT id,name,sex,birthday FROM user WHERE name LIKE ? OR sex LIKE ? OR birthday LIKE ? ";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyWord + "%");
			pstmt.setString(2, "%" + keyWord + "%");
			pstmt.setString(3, "%" + keyWord + "%");
			ResultSet rs = pstmt.executeQuery();//执行查询操作
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setSex(rs.getString(3));
				user.setBirthday(rs.getDate(4));
				all.add(user);// 所有的内容向集合中插入
			}
			rs.close();
		}catch (SQLException e) {
			throw e;
		}finally {//不管如何抛出，最终都进行数据库的关闭操作
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
					throw e;
				}
			}
		}
		return all;
	}
}
