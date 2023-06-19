package com.ttm.crm.server.entity;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ttm.crm.server.history.HistoryAction;
import com.ttm.crm.server.history.MapIdTo;
import com.ttm.crm.server.history.SkipHistoryLogging;
import com.ttm.crm.server.history.TranslateAs;
import com.ttm.crm.server.history.TranslateHistory;

@MapIdTo(value="accountId")
public class Account {	
	
	@NotBlank(message = "Please enter an account type")
	@Pattern(regexp = "Prospect|Active", message = "Please enter a valid account type")
	private String accountType;
	
	private String billingCity = "";
	
	private String billingCountry = "";

	private String billingPostalCode = "";

	private String billingProvinceState = "";

	private String billingStreet = "";
	
	private String currency = "";
	
	@SkipHistoryLogging
	private String currencyDescription = "";
	
	private int id;
	
	private String industry = "";
	
	@SkipHistoryLogging
	private HistoryAction lastChangeAction;

	@SkipHistoryLogging
	private String lastChangeDescription;

	@SkipHistoryLogging
	private Timestamp lastChangeTimestamp;
	
	@SkipHistoryLogging
	private String lastChangeUserName;

	@Size(min=2, message = "Please enter a minimum of 2 characters")
	private String name;
	
	@TranslateHistory(translateAs=TranslateAs.USER, userFieldName="Agent")
	private int agentId;
	
	@SkipHistoryLogging
	private String agentName = "Not assigned"; // display only

	private String phone = "";
	
	private boolean shippingAddressSameAsBilling = false;

	private String shippingCity = "";
	
	private String shippingCountry = "";
	
	private String shippingPostalCode = "";

	private String shippingProvinceState = "";
	
	private String shippingStreet = "";
	
	private String webSite = "";

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	public String getBillingCountry() {
		return billingCountry;
	}
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	public String getBillingStreet() {
		return billingStreet;
	}
	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}
	public String getBillingPostalCode() {
		return billingPostalCode;
	}
	public void setBillingPostalCode(String billingPostalCode) {
		this.billingPostalCode = billingPostalCode;
	}
	public String getBillingProvinceState() {
		return billingProvinceState;
	}
	public void setBillingProvinceState(String billingProvinceState) {
		this.billingProvinceState = billingProvinceState;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrencyDescription() {
		return currencyDescription;
	}
	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getShippingCity() {
		return shippingCity;
	}
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	public String getShippingCountry() {
		return shippingCountry;
	}
	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}
	public String getShippingPostalCode() {
		return shippingPostalCode;
	}
	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}
	public String getShippingProvinceState() {
		return shippingProvinceState;
	}
	public void setShippingProvinceState(String shippingProvinceState) {
		this.shippingProvinceState = shippingProvinceState;
	}
	public String getShippingStreet() {
		return shippingStreet;
	}
	public void setShippingStreet(String shippingStreet) {
		this.shippingStreet = shippingStreet;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
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
	public boolean isShippingAddressSameAsBilling() {
		return shippingAddressSameAsBilling;
	}
	public void setShippingAddressSameAsBilling(boolean shippingAddressSameAsBilling) {
		this.shippingAddressSameAsBilling = shippingAddressSameAsBilling;
	}	
	  
}
