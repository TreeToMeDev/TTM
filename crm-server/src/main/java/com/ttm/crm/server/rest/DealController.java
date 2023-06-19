package com.ttm.crm.server.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Deal;
import com.ttm.crm.server.service.DealService;

@RestController
@RequestMapping("/deals")
public class DealController {

	private DealService dealService;
	
	public DealController(DealService dealService) {
		this.dealService = dealService;
	}
	
	@GetMapping
    public List<Deal> get(@RequestParam("filter") String taskFilter) {
		switch (taskFilter) {
		case "all":
			return dealService.find();
		case "open":
			return dealService.findAllOpen();	
		}
		
		return new ArrayList<Deal>();
	}

	@GetMapping("/account/{accountId}")
    public List<Deal> getByAccountId(@PathVariable("accountId") int accountId, @RequestParam("filter") String filter) {
		switch (filter) {
		case "all":
			return dealService.findAllByAccountId(accountId);
		case "open":
			return dealService.findAllOpenByAccountId(accountId);	
		}	
		return new ArrayList<Deal>();
	}
	
	@GetMapping("/contact/{contactId}")
    public List<Deal> getTasksByContactId(@PathVariable("contactId") int contactId, @RequestParam("filter") String filter) {		
		switch (filter) {
		case "all":
			return dealService.findAllByContactId(contactId);
		case "open":
			return dealService.findAllOpenByContactId(contactId);	
		}
		return new ArrayList<Deal>();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Deal> get(@PathVariable("id") int id) {
		Deal deal = dealService.find(id);
		return ResponseEntity.ok().body(deal);
	}
	
	@PostMapping
	public int post(@Valid @RequestBody Deal deal) {
		return dealService.add(deal);
	}
	
	@PatchMapping("/{id}")
    public ResponseEntity<Deal> patch(@PathVariable("id") int id, @RequestBody Deal deal) {
		deal.setId(id);
		dealService.update(deal);
		return null;
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
		dealService.delete(id);
		return ResponseEntity.ok().body(true);
	}
	
}
