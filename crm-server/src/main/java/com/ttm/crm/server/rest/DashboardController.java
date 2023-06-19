package com.ttm.crm.server.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.entity.Task;
import com.ttm.crm.server.entity.dashboard.AccountCountryTotal;
import com.ttm.crm.server.entity.dashboard.AccountTypeTotal;
import com.ttm.crm.server.service.DashboardService;
import com.ttm.crm.server.service.TaskService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

private DashboardService dashboardService;
	
	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
	@GetMapping("/opentasks/{userId}")
    public List<Task> getOpenTasks(@PathVariable("userId") int userId) {
		
		return dashboardService.findOpenTasks(userId);
	}
	
	@GetMapping("/newleads/{userId}")
    public List<Contact> getNewLeads(@PathVariable("userId") int userId) {
		
		return dashboardService.findNewLeads(userId);
	}
	
	@GetMapping("/accounttypetotals/{userId}")
    public List<AccountTypeTotal> getAccountTypeTotals(@PathVariable("userId") int userId) {	
		return dashboardService.getAccountTypeTotals(userId);
	}
	
	@GetMapping("/accountcountrytotals/{userId}")
    public List<AccountCountryTotal> getAccountCountryTotals(@PathVariable("userId") int userId) {	
		return dashboardService.getAccountCountryTotals(userId);
	}
}
