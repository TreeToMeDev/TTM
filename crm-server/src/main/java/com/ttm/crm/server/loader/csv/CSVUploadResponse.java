package com.ttm.crm.server.loader.csv;

import java.util.List;

import com.ttm.crm.server.entity.FileUpload;

public class CSVUploadResponse {
	
	private FileUpload fileUpload;
	private CSVFileDetails fileDetails;
	private List<String> errors;
	private int postCount;
	
	public FileUpload getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}
	public CSVFileDetails getFileDetails() {
		return fileDetails;
	}
	public void setFileDetails(CSVFileDetails fileDetails) {
		this.fileDetails = fileDetails;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public void addError(String error) {
		this.errors.add(error);
	}
	public int getPostCount() {
		return postCount;
	}
	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}
}
