package com.ttm.crm.server.entity;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;

public class Referral {
	
	@NotBlank(message = "Please enter a company name")
	private String companyName = "";
	
	private boolean consentToContact;
	
	private int contactId = 0;
	
	@NotBlank(message = "Please enter an email address")
	private String email;
	
	@NotBlank(message = "Please enter a first name")
	private String firstName = "";

	private int id;
	
	@NotBlank(message = "Please enter a last name")
	private String lastName = "";
	
	private String jobTitle = "";

	private String notes = "";

	@NotBlank(message = "Please enter a last name")
	private String phone = "";
	
	@NotBlank(message = "Please enter an email address")
	private String referrerEmail = "";

	@NotBlank(message = "Please enter a first name")
	private String referrerFirstName = "";

	@NotBlank(message = "Please enter a last name")
	private String referrerLastName = "";

	@NotBlank(message = "Please enter a phone number")
	private String referrerPhone = "";
	
	private OffsetDateTime submitTimestamp = null;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public boolean isConsentToContact() {
		return consentToContact;
	}

	public void setConsentToContact(boolean consentToContact) {
		this.consentToContact = consentToContact;
	}

	public String getEmail() {
		return email;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReferrerEmail() {
		return referrerEmail;
	}

	public void setReferrerEmail(String referrerEmail) {
		this.referrerEmail = referrerEmail;
	}

	public String getReferrerFirstName() {
		return referrerFirstName;
	}

	public void setReferrerFirstName(String referrerFirstName) {
		this.referrerFirstName = referrerFirstName;
	}

	public String getReferrerLastName() {
		return referrerLastName;
	}

	public void setReferrerLastName(String referrerLastName) {
		this.referrerLastName = referrerLastName;
	}

	public String getReferrerPhone() {
		return referrerPhone;
	}

	public void setReferrerPhone(String referrerPhone) {
		this.referrerPhone = referrerPhone;
	}

	public OffsetDateTime getSubmitTimestamp() {
		return submitTimestamp;
	}

	public void setSubmitTimestamp(OffsetDateTime submitTimestamp) {
		this.submitTimestamp = submitTimestamp;
	}
}
