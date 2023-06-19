package com.ttm.crm.server.entity;

public class ContactType {

	private int contactId;
	private String contactTypeKey;

	public ContactType() { }
	
	//  Used for new Contacts only.
	public ContactType(String contactTypeKey) {
		this.contactTypeKey = contactTypeKey;
	}

	public ContactType(int contactId, String contactTypeKey) {
		this.contactId = contactId;
		this.contactTypeKey = contactTypeKey;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getContactTypeKey() {
		return contactTypeKey;
	}

	public void setContactTypeKey(String contactTypeKey) {
		this.contactTypeKey = contactTypeKey;
	}
}
