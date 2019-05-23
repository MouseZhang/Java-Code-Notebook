##用户管理程序

----

###1.案例要求

- ####要求一：开发一个用户管理程序，其中用户的基本信息包括

  - 用户编号
  - 用户姓名
  - 用户性别
  - 用户生日

- ####要求二：通过程序实现用户的统一管理，将所有的信息保存在数据库之中

----

###2.案例分析

- ####分析一：整个程序在开发代码的时候要先设计接口

  - 实现用户管理的增加、修改、查询和删除功能


- ####分析二：本程序因为牵扯到数据库的问题

  - 使用MySQL数据库，同时采用JDBC操作

- ####分析三：开发使用Eclipse工具完成，这里不采用图形界面

- ####分析四：相关技术涉及面向对象思想、Java类集、JavaIO等概念

----

###3.部分代码实现

####3.1编写数据库的创建脚本

```mysql
DROP TABLE user;
CREATE TABLE user(
	ID	INT	AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(50) NOT NULL,
	SEX VARCHAR(10) NOT NULL,
	BIRTHDAY DATE
);
```

####3.2定义操作用户表的接口IUserDAO

```java
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
```

####3.3定义工厂类DAOFactory，通过工厂设计进行解耦合操作

```java
package org.ustb.humanresource.factory;

import org.ustb.humanresource.dao.IUserDAO;
import org.ustb.humanresource.dao.proxy.IUserDAOProxy;

public class DAOFactory {
	public static IUserDAO getIUserDAOInstance() {
		return new IUserDAOProxy();
	}
}
```

####3.4使用代理设计，对数据库的打开和关闭进行操作，与核心业务操作分离

```java
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
```

----

###4.小结

- 程序设计应该以**接口**为操作标准
- 每个类只完成一个**具体功能**，如果有多个功能，则需要**分类实现**
- 本程序的重点在于数据的**操作类**和**接口**的关系
- **工厂设计**和**代理设计**是在开发中使用最多的设计思路，应重点掌握