package com.ttm.crm.server.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.AppConfig;
import com.ttm.crm.server.service.AppConfigService;

@RestController
@RequestMapping("/appconfig")
public class AppConfigController {

private AppConfigService appConfigService;
	
	public AppConfigController(AppConfigService appConfigService) {
		this.appConfigService = appConfigService;
	}
	
	@GetMapping("/{category}/{configOption}")
    public AppConfig get(@PathVariable("category") String category, @PathVariable("configOption") String configOption) {
		return appConfigService.find(category, configOption);
	}
	
	@GetMapping("/{category}")
    public List<AppConfig> getByCategory(@PathVariable("category") String category) {
		return appConfigService.findByCategory(category);
	}
}
