package com.ttm.crm.server.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.History;
import com.ttm.crm.server.service.HistoryService;

@RestController
@RequestMapping("/history")
public class HistoryController {

	private HistoryService historyService;
	
	public HistoryController(HistoryService historyService) {
		this.historyService = historyService;
	}
	
	@GetMapping("/account/{id}")
	public List<History> getByAccountId(@PathVariable("id") int id) {
		return historyService.fetchByAccountId(id);
	}
	
	@GetMapping("/contact/{id}")
	public List<History> getByContactId(@PathVariable("id") int id) {
		return historyService.fetchByContactId(id);
	}

	@GetMapping("/deal/{id}")
	public List<History> getByDealId(@PathVariable("id") int id) {
		return historyService.fetchByDealId(id);
	}

	@GetMapping("/task/{id}")
	public List<History> getByTaskId(@PathVariable("id") int id) {
		return historyService.fetchByTaskId(id);
	}
	
}
