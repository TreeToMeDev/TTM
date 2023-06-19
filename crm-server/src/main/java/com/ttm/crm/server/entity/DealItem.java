package com.ttm.crm.server.entity;

public class DealItem {

	private long amount;
	private int dealId;
	private int id;
	private float price;
	private int productId;
	private String productCode;
	private String productDescription;
	private String productMeasurement;
	private float productMeasurementAmount;
	private int quantity;
	
	public int getDealId() {
		return dealId;
	}
	public void setDealId(int dealId) {
		this.dealId = dealId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductMeasurement() {
		return productMeasurement;
	}
	public void setProductMeasurement(String productMeasurement) {
		this.productMeasurement = productMeasurement;
	}
	public float getProductMeasurementAmount() {
		return productMeasurementAmount;
	}
	public void setProductMeasurementAmount(float productMeasurementAmount) {
		this.productMeasurementAmount = productMeasurementAmount;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	/*private String name;
	
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
	
	@TranslateHistory(translateAs=TranslateAs.USER, userFieldName="Owner")
	private int ownerUserId;
	
	@SkipHistoryLogging
	private String ownerUserName;
	
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

	public int getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(int ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}

	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}*/
	
}
