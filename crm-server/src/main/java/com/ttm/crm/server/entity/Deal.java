package com.ttm.crm.server.entity;

import java.time.OffsetDateTime;

import com.ttm.crm.server.history.SkipHistoryLogging;
import com.ttm.crm.server.history.TranslateAs;
import com.ttm.crm.server.history.TranslateHistory;

public class Deal {

	private long amount;
	
	private String name;
	
	@TranslateHistory(translateAs=TranslateAs.ACCOUNT, userFieldName="Account")
	private int accountId;
	
	@SkipHistoryLogging
	private String accountName;
	
	@TranslateHistory(translateAs=TranslateAs.CONTACT, userFieldName="Contact")
	private int contactId;
	
	@SkipHistoryLogging
	private String contactName;
	
	private OffsetDateTime dueDate;
	
	private int id;
	
	@TranslateHistory(translateAs=TranslateAs.USER, userFieldName="Agent")
	private int agentId;
	
	@SkipHistoryLogging
	private String agentName;
	
	private String stage;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
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

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

}
