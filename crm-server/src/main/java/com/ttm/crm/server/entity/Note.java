package com.ttm.crm.server.entity;

import java.sql.Timestamp;

import com.ttm.crm.server.history.SkipHistoryLogging;
import com.ttm.crm.server.history.TranslateAs;
import com.ttm.crm.server.history.TranslateHistory;

public class Note {
	
	private int accountId;
	
	private int contactId;
	
	private int id;
	
	private int referralId;
	
	private String text;
	
	private Timestamp timeStamp;
	
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
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReferralId() {
		return referralId;
	}
	public void setReferralId(int referralId) {
		this.referralId = referralId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
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
	
}