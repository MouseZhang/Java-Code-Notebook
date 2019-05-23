package org.fangsoft.testcenter.model;

import java.io.Serializable;

public class Customer implements Serializable{
	private static final long serialVersionUID = 1219411723954779148L;
	private long id;
	private String userId;
	private String password;
	private String email;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
