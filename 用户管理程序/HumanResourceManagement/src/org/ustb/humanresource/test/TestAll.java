package org.ustb.humanresource.test;


import java.util.Iterator;
import java.util.List;

import org.ustb.humanresource.factory.DAOFactory;
import org.ustb.humanresource.vo.User;

public class TestAll {
	public static void main(String[] args) throws Exception {
		List<User> allUsers = DAOFactory.getIUserDAOInstance().findAll("");
		Iterator<User> iter = allUsers.iterator();
		while (iter.hasNext()) {
			User user = iter.next();
			System.out.println(user);
		}
	}
}
