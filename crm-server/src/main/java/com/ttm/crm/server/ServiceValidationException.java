package com.ttm.crm.server;

public class ServiceValidationException extends RuntimeException {
	
	public String fieldName;
	public String message;
	
	public ServiceValidationException(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}
	
}
