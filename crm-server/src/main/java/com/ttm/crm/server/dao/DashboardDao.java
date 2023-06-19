package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.entity.Task;
import com.ttm.crm.server.entity.dashboard.AccountCountryTotal;
import com.ttm.crm.server.entity.dashboard.AccountTypeTotal;
import com.ttm.crm.server.service.SessionService;

@Component
public class DashboardDao {

	private final SessionService sessionService;
	private final SqlSession sqlSession;
	
	public DashboardDao(SessionService sessionService, SqlSession sqlSession) {
		this.sessionService = sessionService;
		this.sqlSession = sqlSession;
	}
	
	public List<Task> findOpenTasks(int userId) {
		//int userId = sessionService.getSession().getUserId();
		return this.sqlSession.selectList("selectDashboardOpenTasks", userId);
	}
	
	public List<Contact> findNewLeads(int userId) {
		//int userId = sessionService.getSession().getUserId();
		return this.sqlSession.selectList("selectDashboardNewLeads", userId);
	}
	
	public List<AccountTypeTotal> getAccountTypeTotals(int userId) {
		return this.sqlSession.selectList("selectDashboardAccountTypeTotals", userId);
	}
	
	public List<AccountCountryTotal> getAccountCountryTotals(int userId) {
		return this.sqlSession.selectList("selectDashboardAccountCountryTotals", userId);
	}
}
