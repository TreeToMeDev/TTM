package com.ttm.crm.server.entity;

import java.time.OffsetDateTime;

import com.ttm.crm.server.history.SkipHistoryLogging;
import com.ttm.crm.server.history.TranslateAs;
import com.ttm.crm.server.history.TranslateHistory;

public class Attachment {

	private int accountId;
	private OffsetDateTime createTimestamp;
	private int id;
	private String name;
	private long size;
	private String storageId;
	@TranslateHistory(translateAs=TranslateAs.USER, userFieldName="Owner")
	private int userId;
	
	@SkipHistoryLogging
	private String userName;
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public OffsetDateTime getCreateTimestamp() {
		return createTimestamp;
	}
	public void setCreateTimestamp(OffsetDateTime createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
	public String getStorageId() {
		return storageId;
	}
	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
}
