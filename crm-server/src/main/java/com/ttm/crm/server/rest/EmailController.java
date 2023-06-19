package com.ttm.crm.server.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Email;
import com.ttm.crm.server.entity.EmailRequest;
import com.ttm.crm.server.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
	
	private final EmailService emailService;
	
	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@GetMapping("{contactId}")
	// TODO: harmonize EmailRequest, Email classes
	public List<Email> getByContact(@PathVariable("contactId") int contactId) {
		return this.emailService.findByContact(contactId);
	}
	
	@PostMapping
	public void post(@RequestBody EmailRequest emailRequest) {
		emailService.send(emailRequest.body, emailRequest.recipient, emailRequest.subject);
	}
	
}