package com.ttm.crm.server.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.ContactTypeList;
import com.ttm.crm.server.service.ContactTypeListService;

@RestController
@RequestMapping("/contacttypelist")
public class ContactTypeListController {

	private ContactTypeListService contactTypeListService;
	
	public ContactTypeListController(ContactTypeListService contactTypeListService) {
		this.contactTypeListService = contactTypeListService;
	}

	@GetMapping()
    public List<ContactTypeList> getAllContactTypeList() {
		return this.contactTypeListService.find();
	}
}
