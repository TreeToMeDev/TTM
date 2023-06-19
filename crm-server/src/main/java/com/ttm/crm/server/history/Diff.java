package com.ttm.crm.server.history;

public class Diff {
	public String fieldName;
	public String newValue;
	public String oldValue;
	public Diff(String fieldName, String oldValue, String newValue) {
		super();
		this.fieldName = fieldName;
		this.newValue = newValue;
		this.oldValue = oldValue;
	}
}
