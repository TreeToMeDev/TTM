package com.ttm.crm.server.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ttm.crm.server.response.FileUploadResponse;
import com.ttm.crm.server.service.FileUploadService;

@RestController
@RequestMapping("/file")
public class FileUploadController {
	
	private FileUploadService fileUploadService;
	
	public FileUploadController(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}
	
	@PostMapping("/upload/contacts")
    public ResponseEntity<FileUploadResponse> uploadContacts(@RequestParam(name = "file", required = false) MultipartFile file) {
        FileUploadResponse response = this.fileUploadService.uploadFile(file);
        return ResponseEntity.ok().body(response);
    }
	
	@PostMapping("/post/contacts")
    public ResponseEntity<FileUploadResponse> postContacts(String fileCode) {
		FileUploadResponse response = this.fileUploadService.postFile(fileCode);
        return ResponseEntity.ok().body(response);
	}
}
