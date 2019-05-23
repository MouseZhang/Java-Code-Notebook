package org.ustb.humanresource.test;

import java.util.Date;

import org.ustb.humanresource.factory.DAOFactory;
import org.ustb.humanresource.vo.User;

public class TestInsert {
	public static void main(String[] args) throws Exception {
		User user = new User();
		user.setName("MouseZhang");
		user.setSex("男");
		user.setBirthday(new Date());
		DAOFactory.getIUserDAOInstance().doCreate(user);
	}
}
