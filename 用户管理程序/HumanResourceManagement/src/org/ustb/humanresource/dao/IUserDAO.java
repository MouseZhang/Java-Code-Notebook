package org.ustb.humanresource.dao;


import java.util.List;

import org.ustb.humanresource.vo.User;

public interface IUserDAO {
	/**
	 * 表示数据库的增加操作
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean doCreate(User user) throws Exception;
	
	/**
	 * 表示数据库的更新操作
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean doUpdate(User user) throws Exception;
	
	/**
	 * 表示数据库的删除操作，按编号删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean doDelete(int id) throws Exception;
	
	/**
	 * 表示数据库的查询操作，按照编号查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public User findById(int id) throws Exception;
	
	/**
	 * 表示数据库的查询操作，返回一组对象
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public List<User> findAll(String keyWord) throws Exception;
}
