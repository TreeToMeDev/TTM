package com.ttm.crm.server.entity;

import java.sql.Timestamp;
import java.util.List;

import com.ttm.crm.server.history.HistoryAction;
import com.ttm.crm.server.history.MapIdTo;
import com.ttm.crm.server.history.SkipHistoryLogging;
import com.ttm.crm.server.history.TranslateAs;
import com.ttm.crm.server.history.TranslateHistory;

@MapIdTo(value="contactId")
public class Contact {

	@TranslateHistory(translateAs=TranslateAs.ACCOUNT, userFieldName="Account")
	private int accountId;
	
	private int id;
	
	@TranslateHistory(translateAs=TranslateAs.USER, userFieldName="Agent")
	private int agentId = 22;

	@SkipHistoryLogging
	private String accountCity;
	
	@SkipHistoryLogging
	private String accountCountry;
	
	@SkipHistoryLogging
	private String accountIndustry;

	@SkipHistoryLogging
	private String accountName;
	
	@SkipHistoryLogging
	private String accountPhone;
	
	@SkipHistoryLogging
	private String accountWebSite;
	
	// TODO: not sure what to do here
	@SkipHistoryLogging
	private int referralId;
	
	@SkipHistoryLogging
	private String referrerName;
	
	// TODO: not sure what to do here
	@SkipHistoryLogging
	private int fileId;
	
	@SkipHistoryLogging
	private String originalFileName;
	
	private String firstName = "";
	
	private String lastName = "";
	
	private String email  = "";
	
	private String phone  = "";
	
	private String title  = "";
	
	private String phoneCell = "";
	
	private String department = "";
	
	private String street = "";
	
	private String city = "";
	
	@SkipHistoryLogging
	private String agentName = "Not assigned";
	
	private String provinceState = "";
	
	private String postalCode = "";
	
	private String country = "";
	
	private String notes = "";
	
	private String source = "";
	
	@SkipHistoryLogging
	private Timestamp addTimestamp;
	
	@SkipHistoryLogging
	private HistoryAction lastChangeAction;
	
	@SkipHistoryLogging
	private String lastChangeDescription;

	@SkipHistoryLogging
	private Timestamp lastChangeTimestamp;
	
	@SkipHistoryLogging
	private String lastChangeUserName;
	
	private List<ContactType> contactTypes;
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public int getReferralId() {
		return referralId;
	}
	public void setReferralId(int referralId) {
		this.referralId = referralId;
	}
	public String getReferrerName() {
		return referrerName;
	}
	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhoneCell() {
		return phoneCell;
	}
	public void setPhoneCell(String phoneCell) {
		this.phoneCell = phoneCell;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvinceState() {
		return provinceState;
	}
	public void setProvince(String provinceState) {
		this.provinceState = provinceState;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
	public void setProvinceState(String provinceState) {
		this.provinceState = provinceState;
	}
	public Timestamp getLastChangeTimestamp() {
		return lastChangeTimestamp;
	}
	public void setLastChangeTimestamp(Timestamp lastChangeTimestamp) {
		this.lastChangeTimestamp = lastChangeTimestamp;
	}
	public String getLastChangeUserName() {
		return lastChangeUserName;
	}
	public void setLastChangeUserName(String lastChangeUserName) {
		this.lastChangeUserName = lastChangeUserName;
	}
	public String getLastChangeDescription() {
		return lastChangeDescription;
	}
	public void setLastChangeDescription(String lastChangeDescription) {
		this.lastChangeDescription = lastChangeDescription;
	}
	public HistoryAction getLastChangeAction() {
		return lastChangeAction;
	}
	public void setLastChangeAction(HistoryAction lastChangeAction) {
		this.lastChangeAction = lastChangeAction;
	}
	public Timestamp getAddTimestamp() {
		return addTimestamp;
	}
	public void setAddTimestamp(Timestamp addTimestamp) {
		this.addTimestamp = addTimestamp;
	}
	public String getAccountCity() {
		return accountCity;
	}
	public void setAccountCity(String accountCity) {
		this.accountCity = accountCity;
	}
	public String getAccountCountry() {
		return accountCountry;
	}
	public void setAccountCountry(String accountCountry) {
		this.accountCountry = accountCountry;
	}
	public String getAccountIndustry() {
		return accountIndustry;
	}
	public void setAccountIndustry(String accountIndustry) {
		this.accountIndustry = accountIndustry;
	}
	public String getAccountPhone() {
		return accountPhone;
	}
	public void setAccountPhone(String accountPhone) {
		this.accountPhone = accountPhone;
	}
	public String getAccountWebSite() {
		return accountWebSite;
	}
	public void setAccountWebSite(String accountWebSite) {
		this.accountWebSite = accountWebSite;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public List<ContactType> getContactTypes() {
		return contactTypes;
	}
	public void setContactTypes(List<ContactType> contactTypes) {
		this.contactTypes = contactTypes;
	}
}
