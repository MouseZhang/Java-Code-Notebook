package org.ustb.humanresource.test;

import org.ustb.humanresource.factory.DAOFactory;

public class TestDelete {
	public static void main(String[] args) throws Exception {
		DAOFactory.getIUserDAOInstance().doDelete(2);
	}
}
