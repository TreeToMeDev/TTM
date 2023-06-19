package com.ttm.crm.server.entity;

import java.sql.Timestamp;

import com.ttm.crm.server.history.EntityInfo;
import com.ttm.crm.server.history.HistoryAction;

public class History {

	private int accountId;
	private HistoryAction action;
	private int contactId;
	private String fieldName = "";
	private int id;
	private String newValue = "";
	private String oldValue = "";
	private int recordId;
	private String tableName;
	private Timestamp timeStamp;
	private int userId;
	private String userName;
	
	public History() {}

	public History(HistoryAction historyAction, Object object){
		if(historyAction == HistoryAction.ADD || historyAction == HistoryAction.DELETE) {
			EntityInfo entityInfo = new EntityInfo(object);
			this.accountId = entityInfo.getAccountId();
			this.action = historyAction;
			this.contactId = entityInfo.getContactId();
			this.recordId = entityInfo.getId();
			this.tableName = entityInfo.getTableName();
		} else {
			throw new RuntimeException("bad History constructor call");
		}	
	}
	
	public History(int accountId, int contactId, String fieldName, String oldValue, String newValue, int recordId,  String tableName){
		this.accountId = accountId;
		this.action = HistoryAction.CHANGE;
		this.contactId = contactId;		
		this.fieldName = prettify(fieldName);
		this.newValue = newValue;
		this.oldValue = oldValue;
		this.recordId = recordId;
		this.tableName = tableName;
	}
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public HistoryAction getAction() {
		return action;
	}
	public void setHistory(HistoryAction historyAction) {
		this.action = historyAction;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
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
	
	public String getDescription() {
		if(action == HistoryAction.CHANGE) {
			String oldValueClean = oldValue;
			String newValueClean = newValue;
			if(oldValueClean.equals("")) {
				oldValueClean = "<blank>";
			}
			if(newValueClean.equals("")) {
				newValueClean = "<blank>";
			}
			String description = tableName + " " + fieldName;
			return description + " from '" + oldValueClean + "' to '" + newValueClean + "'";
		} else {
			return tableName;
		}
	}
	
	private String prettify(String s) {
		// turn eg. billingAddressCity into Billing Address City
		String sArray[] = s.split("(?=\\p{Upper})");
		String ret = "";
		for(String str: sArray) {
			if(ret == "") {
				ret = str.substring(0, 1).toUpperCase() + str.substring(1);
			} else {
				ret = ret + " " + str;
			}
		}
		return ret;
	}

	public void setAction(HistoryAction historyAction) {
		this.action = historyAction;
	}
	
}
