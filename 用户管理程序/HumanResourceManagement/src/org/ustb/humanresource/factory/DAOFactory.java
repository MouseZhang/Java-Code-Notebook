package org.ustb.humanresource.factory;

import org.ustb.humanresource.dao.IUserDAO;
import org.ustb.humanresource.dao.proxy.IUserDAOProxy;

public class DAOFactory {
	public static IUserDAO getIUserDAOInstance() {
		return new IUserDAOProxy();
	}
}
