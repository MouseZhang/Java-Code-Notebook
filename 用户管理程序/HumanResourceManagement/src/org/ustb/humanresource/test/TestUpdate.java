package org.ustb.humanresource.test;

import java.util.Date;

import org.ustb.humanresource.factory.DAOFactory;
import org.ustb.humanresource.vo.User;

public class TestUpdate {
	public static void main(String[] args) throws Exception {
		User user = new User();
		user.setName("PeiPei");
		user.setSex("女");
		user.setBirthday(new Date());
		user.setId(3);
		DAOFactory.getIUserDAOInstance().doUpdate(user);
	}
}
