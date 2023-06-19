package com.ttm.crm.server.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ttm.crm.server.entity.Attachment;
import com.ttm.crm.server.filestorage.googledrive.service.GoogleDriveService;
import com.ttm.crm.server.service.AttachmentService;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {

	private AttachmentService attachmentService;
	private GoogleDriveService googleDriveService;
	
	public AttachmentController(AttachmentService attachmentService, GoogleDriveService googleDriveService) {
		this.attachmentService = attachmentService;
		this.googleDriveService = googleDriveService;
	}

	@GetMapping("{id}")
    public Attachment get(@PathVariable("id") int id) {
		return this.attachmentService.find(id);
	}

	@GetMapping("accounts/{accountId}")
    public List<Attachment> getByAccount(@PathVariable("accountId") int accountId) {
		return this.attachmentService.findByAccount(accountId);
	}
	
	@GetMapping("accounts/{accountId}/attachments/{attachmentId}")
	public ResponseEntity<String> getAttachmentFile(@PathVariable("accountId") int accountId,
									@PathVariable("attachmentId") int attachmentId) {
		
		Attachment attachment = this.attachmentService.find(attachmentId);
		
		System.out.println(attachment.getName());
		
		String link = this.googleDriveService.getFile(attachment.getStorageId());
		
		link = "{ \"webLink\": \"" + link + "\"}";
		return ResponseEntity.ok().body(link);
	}

	// TODO proper return type w/edits etc.
	// TODO use DAO numb!!
	@PostMapping
    public ResponseEntity<Attachment> post(@RequestBody Attachment attachment) {
		attachmentService.add(attachment);
		return null;
	}

	@PostMapping("/accounts/{accountId}")
    public ResponseEntity<String> addToAccount(@PathVariable("accountId") int accountId,
    										   @RequestParam(required = false) MultipartFile file) {
		
		List<String> contentTypes = Arrays.asList("image/png", "image/jpeg", "image/gif", 
												  "application/pdf", 
												  "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
												  "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
												  "text/csv");
		
		String fileContentType = file.getContentType();
	    if(!contentTypes.contains(fileContentType)) {
	    	System.out.println(fileContentType);
	    	throw new RuntimeException("Invalid File Type");
	    }
	    
		String storageFileId = this.googleDriveService.addFileToAccount(accountId, file);
		
		Attachment newAttachment = new Attachment();
		
		newAttachment.setAccountId(accountId);
		newAttachment.setName(file.getOriginalFilename());
		newAttachment.setSize(file.getSize());
		newAttachment.setStorageId(storageFileId);
		
		this.attachmentService.add(newAttachment);
	    return ResponseEntity.ok().body("");	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
		
		Attachment oldAttachment = this.attachmentService.find(id);
		this.googleDriveService.deleteFileFromAccount(oldAttachment.getStorageId());
		
		this.attachmentService.delete(id);
		return ResponseEntity.ok().body(true);
	}
}
