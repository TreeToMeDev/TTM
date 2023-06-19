package com.ttm.crm.server.entity;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.ttm.crm.server.loader.csv.FileStatus;

public class FileUpload {
	
	private String fileCode;
	
	@NotBlank(message = "Please enter a file name")
	private String fileName;
	
	@NotBlank(message = "Please enter content type")
	@Pattern(regexp = "Contact", message = "Please enter valid content type")
	private String fileContentType;
	
	private int id;
	private OffsetDateTime lastTimestamp;
	private String originalFileName;
	private String lastUser;
	private FileStatus status;
	
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
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
	public FileStatus getStatus() {
		return status;
	}
	public void setStatus(FileStatus status) {
		this.status = status;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
}
