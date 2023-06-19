package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.dao.AppConfigDao;
import com.ttm.crm.server.entity.AppConfig;

@Service
public class AppConfigService {

private AppConfigDao appConfigDao;
	
	@Autowired
	public AppConfigService(AppConfigDao appConfigDao) {
		this.appConfigDao = appConfigDao;
	}
	
	public AppConfig find(String category, String configOption) {
		return appConfigDao.find(category, configOption);
	}

	public List<AppConfig> findByCategory(String category) {
		return appConfigDao.findAllByCategory(category);
	}
}
