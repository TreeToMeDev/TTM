package com.ttm.crm.server.entity;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;

import com.ttm.crm.server.history.SkipHistoryLogging;

public class Product {

	private OffsetDateTime availableDate;
	
	@NotBlank(message = "Please enter a description")
	private String description;
	
	private int id;
	
	@NotBlank(message = "Please enter a product measurement")
	private String measurement;
	
	private float measurementAmount;
	
	@SkipHistoryLogging
	private String measurementDescription;
	
	private float price;
	
	@NotBlank(message = "Please enter a product code")
	private String productCode;
	
	private int quantityOnHand;

	private String warrantyDuration;
	
	public OffsetDateTime getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(OffsetDateTime availableDate) {
		this.availableDate = availableDate;
	}

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

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public float getMeasurementAmount() {
		return measurementAmount;
	}

	public void setMeasurementAmount(float measurementAmount) {
		this.measurementAmount = measurementAmount;
	}

	public String getMeasurementDescription() {
		return measurementDescription;
	}

	public void setMeasurementDescription(String measurementDescription) {
		this.measurementDescription = measurementDescription;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public int getQuantityOnHand() {
		return quantityOnHand;
	}

	public void setQuantityOnHand(int quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}

	public String getWarrantyDuration() {
		return warrantyDuration;
	}

	public void setWarrantyDuration(String warrantyDuration) {
		this.warrantyDuration = warrantyDuration;
	}

}
