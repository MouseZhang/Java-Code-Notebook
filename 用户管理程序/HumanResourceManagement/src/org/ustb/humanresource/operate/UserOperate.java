package org.ustb.humanresource.operate;

import java.util.Iterator;
import java.util.List;

import org.ustb.humanresource.factory.DAOFactory;
import org.ustb.humanresource.util.InputData;
import org.ustb.humanresource.vo.User;

public class UserOperate {
	public static void insert() {
		User user = new User();
		InputData input = new InputData();
		user.setName(input.getString("请输入姓名："));;
		user.setSex(input.getString("请输入性别："));
		user.setBirthday(input.getDate("请输入生日：", "内容必须是日期(yyyy-MM-dd)！"));
		try {
			DAOFactory.getIUserDAOInstance().doCreate(user);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void update() {
		InputData input = new InputData();
		int id = input.getInt("请输入要修改用户的编号：", "编号必须是数字！");
		User user = null;
		try {
			user = DAOFactory.getIUserDAOInstance().findById(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null) {
			user.setName(input.getString("请重置姓名，(原姓名是 " + user.getName() + ")：" ));
			user.setSex(input.getString("请重置性别，(原性别是 " + user.getSex() + ")：" ));
			user.setBirthday(input.getDate("请重置生日，(原生日是 " + user.getBirthday() + ")：" , 
				"日期格式必须是(yyyy-MM-dd)！"));
			try {
				DAOFactory.getIUserDAOInstance().doUpdate(user);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("要查找的用户不存在！");
		}
	}
	
	public static void delete() {
		InputData input = new InputData();
		int id = input.getInt("请输入要删除用户的编号：", "编号必须是数字！");
		try {
			boolean flag = DAOFactory.getIUserDAOInstance().doDelete(id);
			if (flag) {
				System.out.println("删除成功！");
			}
			else {
				System.out.println("删除失败！");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void findId() {
		InputData input = new InputData();
		int id = input.getInt("请输入要查询用户的编号：", "编号必须是数字！");
		User user = null;
		try {
			user = DAOFactory.getIUserDAOInstance().findById(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null) {
			System.out.println(user);
		}
		else {
			System.out.println("要查询的用户不存在！");
		}
	}
	
	public static void findAll() {
		InputData input = new InputData();
		String keyWord = input.getString("请输入要查询的关键字：");
		List<User> allUsers = null;
		try {
			allUsers = DAOFactory.getIUserDAOInstance().findAll(keyWord);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (allUsers != null) {
			Iterator<User> iter = allUsers.iterator();
			while (iter.hasNext()) {
				User user = iter.next();
				System.out.println(user);
			}
		}
		else {
			System.out.println("要查询的用户不存在！");
		}
	}
}
