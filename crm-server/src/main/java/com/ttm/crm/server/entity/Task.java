package com.ttm.crm.server.entity;

import java.time.OffsetDateTime;

import com.ttm.crm.server.history.SkipHistoryLogging;
import com.ttm.crm.server.history.TranslateAs;
import com.ttm.crm.server.history.TranslateHistory;

public class Task {

	
	@TranslateHistory(translateAs=TranslateAs.ACCOUNT, userFieldName="Account")
	private int accountId;
	
	@SkipHistoryLogging
	private String accountName;
	
	@TranslateHistory(translateAs=TranslateAs.USER, userFieldName="Agent")
	private int agentId;
	
	@SkipHistoryLogging
	private String agentName;
	
	@TranslateHistory(translateAs=TranslateAs.CONTACT, userFieldName="Contact")
	private int contactId;
	
	@SkipHistoryLogging
	private String contactName;
	
	private String description;
	
	// TODO why OffsetDateTime here and elsewhere just Date?
	private OffsetDateTime dueDate;
	
	private int id;
	
	private String notes;
	
	private String priority;
	
	private String status;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OffsetDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(OffsetDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
}
