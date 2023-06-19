package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ttm.crm.server.Denullifier;
import com.ttm.crm.server.dao.FileUploadDao;
import com.ttm.crm.server.entity.FileUpload;
import com.ttm.crm.server.loader.csv.CSVFileLoader;
import com.ttm.crm.server.loader.csv.CSVUploadResponse;
import com.ttm.crm.server.response.FileUploadResponse;

@Service
public class FileUploadService {

	private FileUploadDao fileUploadDao;
	private CSVFileLoader csvLoader;
	
	@Autowired
	public FileUploadService(FileUploadDao fileUploadDao, CSVFileLoader csvLoader) {
		this.fileUploadDao = fileUploadDao;
		this.csvLoader = csvLoader;
	}
  
	public void add(FileUpload fileUpload) {
		Denullifier.denullify(fileUpload);
		fileUploadDao.add(fileUpload);
	}

	public void update(FileUpload fileUpload) {
		Denullifier.denullify(fileUpload);
		fileUploadDao.update(fileUpload);
	}
	
	public List<FileUpload> find() {
		return fileUploadDao.find();
	}

	public FileUpload find(int id) {
		return fileUploadDao.find(id);
	}
	
	public FileUpload findByFileName(String fileName) {
		return fileUploadDao.findByFileName(fileName);
	}
	
	public FileUploadResponse uploadFile(MultipartFile file) {	
		CSVUploadResponse csvResponse = this.csvLoader.uploadFile(file);
		FileUploadResponse fur = new FileUploadResponse();
		fur.setCsvResponse(csvResponse);
		return fur;
	}
	
	public FileUploadResponse postFile(String fileCode) {
		
		FileUploadResponse fpr = new FileUploadResponse();
		CSVUploadResponse csvResponse = this.csvLoader.postFile(fileCode);
		fpr.setCsvResponse(csvResponse);
		return fpr;
		
	}
}
