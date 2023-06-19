package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.dao.DashboardDao;
import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.entity.Task;
import com.ttm.crm.server.entity.dashboard.AccountCountryTotal;
import com.ttm.crm.server.entity.dashboard.AccountTypeTotal;

@Service
public class DashboardService {

	private DashboardDao dashboardDao;

	@Autowired
	public DashboardService(DashboardDao dashboardDao) {
		this.dashboardDao = dashboardDao;
	}
	
	public List<Task> findOpenTasks(int userId) {
		return dashboardDao.findOpenTasks(userId);
	}
	
	public List<Contact> findNewLeads(int userId) {
		return dashboardDao.findNewLeads(userId);
	}
	
	public List<AccountTypeTotal> getAccountTypeTotals(int userId) {
		return dashboardDao.getAccountTypeTotals(userId);
	}
	
	public List<AccountCountryTotal> getAccountCountryTotals(int userId) {
		return dashboardDao.getAccountCountryTotals(userId);
	}
}
