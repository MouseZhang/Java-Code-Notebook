package org.fangsoft.testcenter.dao.impl;

import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.data.CustomerData;
import org.fangsoft.testcenter.model.Customer;

public class CustomerDaoImpl implements CustomerDao {
	private static final CustomerDao cdao = new CustomerDaoImpl();
	public static final CustomerDao getInstance() {
		return cdao;
	}
	private CustomerDaoImpl(){};
	public Customer login(String userId, String password) {
		Customer[] customers = CustomerData.getCustomer();
		for (int i = 0; i < customers.length; i++) {
			Customer c = customers[i];
			if (c.getUserId().equals(userId) && c.getPassword().equals(password)) {
				return customers[i];
			}
		}
		return null;
	}
}
