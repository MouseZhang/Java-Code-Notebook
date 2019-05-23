package org.fangsoft.testcenter.dao;

import org.fangsoft.testcenter.model.Customer;

public interface CustomerDao {
	public Customer login(String userId, String password);
}
