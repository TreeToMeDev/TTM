package com.ttm.crm.server.entity;

import java.time.OffsetDateTime;

public class AppConfig {

	private String category;
	
	private String configOption;
	
	private String description;
	
	private int id;
	
	private OffsetDateTime lastTimestamp;
	
	private String lastUser;
	
	private String textValue;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getConfigOption() {
		return configOption;
	}

	public void setConfigOption(String configOption) {
		this.configOption = configOption;
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

	public OffsetDateTime getLastTimestamp() {
		return lastTimestamp;
	}

	public void setLastTimestamp(OffsetDateTime lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
}
