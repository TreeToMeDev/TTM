package com.ttm.crm.server.entity;

import java.util.Date;

public class Purchase {
	
	private int accountId;
	private int id;
	private String productCode;
	private Date purchaseDate;
	private String serialNo;
	private Date warrantyEndDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Date getWarrantyEndDate() {
		return warrantyEndDate;
	}
	public void setWarrantyEndDate(Date warrantyEndDate) {
		this.warrantyEndDate = warrantyEndDate;
	}
	public int getAccountId() {
		return this.accountId;
	}
	public void setCustomerCode(int accountId) {
		this.accountId = accountId;
	}	

}
