package org.ustb.humanresource.test;

import org.ustb.humanresource.factory.DAOFactory;
import org.ustb.humanresource.vo.User;

public class TestId {
	public static void main(String[] args) throws Exception {
		User user = DAOFactory.getIUserDAOInstance().findById(3);
		System.out.println(user);
	}
}
