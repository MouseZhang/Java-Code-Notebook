package org.fangsoft.testcenter.dao.prop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.dao.DaoIOConfig;
import org.fangsoft.testcenter.data.CustomerData;
import org.fangsoft.testcenter.model.Customer;

public class CustomerPropDao implements CustomerDao{
	private static final CustomerPropDao cdao = new CustomerPropDao();
	public static final CustomerPropDao getInstance() {
		return cdao;
	}
	private CustomerPropDao(){};
	public Customer findByUserId(String userId) {
		String path = DaoIOConfig.getCustomerFilePath();
		File[] files = new File(path).listFiles();
		if (files == null || files.length == 0) {
			return null;
		}
		for (File f: files) {
			String fileName = f.getName();
			if (f.isFile() && fileName.startsWith(userId)) {
				Properties ps = new Properties();
				try {
					ps.load(new FileReader(f));
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				}
				return Property2Object.toCustomer(ps);
			}
		}
		return null;
	}
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
	public void save(Customer customer) {
		Properties ps = Property2Object.toProperties(customer);
		String path = DaoIOConfig.getCustomerFilePath();
		String fileName = DaoIOConfig.getFileName(customer);
		new File(path).mkdirs();
		try {
			ps.store(new FileWriter(path + fileName), "");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
