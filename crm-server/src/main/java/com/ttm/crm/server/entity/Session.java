package com.ttm.crm.server.entity;

public class Session {

	private boolean accessUsers;
	public int userId;
	private int parentId;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAccessUsers() {
		return accessUsers;
	}
	public void setAccessUsers(boolean accessUsers) {
		this.accessUsers = accessUsers;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
