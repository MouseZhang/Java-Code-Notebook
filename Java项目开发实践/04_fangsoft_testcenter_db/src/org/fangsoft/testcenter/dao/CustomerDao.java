package org.fangsoft.testcenter.dao;

import org.fangsoft.testcenter.model.Customer;

public interface CustomerDao {
	public void save(Customer customer);
	public Customer findByUserId(String userId);
	public Customer login(String userId, String password);
}
