package com.ttm.crm.server.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.service.ContactService;

@RestController
@RequestMapping("/contacts")
public class ContactController {

	private ContactService contactService;
	
	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}
	
	@GetMapping("account/{accountId}")
    public List<Contact> getContactsByAccountId(@PathVariable("accountId") int accountId) {
        List<Contact> contacts = this.contactService.findByAccountId(accountId);
    	return contacts;
    }
	
	@GetMapping("/{contactId}")
    public Contact getContactById(@PathVariable("contactId") int contactId) {
        return this.contactService.find(contactId);
    }
	
	@GetMapping
    public List<Contact> getContacts() {
		List<Contact> contacts = this.contactService.findAllContacts();
    	return contacts;
    }
	
	@PostMapping
    public void newCustomerContact(@RequestBody Contact contact) {
		contact.setSource("MANUAL");
		this.contactService.add(contact);
	}
	
	@PatchMapping("/{id}")
	public void updateCustomerContact(@PathVariable("id") int id, @RequestBody Contact contact) {
		contact.setId(id);
		this.contactService.update(contact);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
		contactService.delete(id);
		return ResponseEntity.ok().body(true);
	}
	
}
