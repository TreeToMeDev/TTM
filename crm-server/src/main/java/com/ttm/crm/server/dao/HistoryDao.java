package com.ttm.crm.server.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.History;
import com.ttm.crm.server.service.AccountService;
import com.ttm.crm.server.service.ContactService;
import com.ttm.crm.server.service.DealService;
import com.ttm.crm.server.service.SessionService;
import com.ttm.crm.server.service.TaskService;

@Component
public class HistoryDao {

	//private final AccountService accountService;
	//private final ContactService contactService;
	//private final DealService dealService;
	private final SessionService sessionService;
	//private final TaskService taskService;
	private final SqlSession sqlSession;
	
	public HistoryDao(/*AccountService accountService, ContactService contactService, DealService dealService,*/ SqlSession sqlSession, SessionService sessionService /*, TaskService taskService*/) {
		//this.accountService = accountService;
		//this.contactService = contactService;
		//this.dealService = dealService;
		this.sessionService = sessionService;
		this.sqlSession = sqlSession;
		//this.taskService = taskService;
	}
	
	public List<History> fetchByAccountId(int accountId) {
		//this.accountService.checkAccess(accountId);
		return this.sqlSession.selectList("selectHistoryAllByAccountId", accountId);
	}
	
	public List<History> fetchByContactId(int contactId) {
		//this.contactService.checkAccess(contactId);
		return this.sqlSession.selectList("selectHistoryAllByContactId", contactId);
	}

	public List<History> fetchByDealId(int dealId) {
		//this.dealService.checkAccess(dealId);
		return this.sqlSession.selectList("selectHistoryAllByDealId", dealId);
	}

	public List<History> fetchByTaskId(int taskId) {
		//this.taskService.checkAccess(taskId);
		return this.sqlSession.selectList("selectHistoryAllByTaskId", taskId);
	}

	public History findLatestByAccountId(int accountId) {
		//this.accountService.checkAccess(accountId);
		return this.sqlSession.selectOne("selectHistoryLatestByAccountId", accountId);
	}	
	
	public History findLatestByContactId(int contactId) {
		//this.contactService.checkAccess(contactId);
		return this.sqlSession.selectOne("selectHistoryLatestByContactId", contactId);
	}	

	public History findFirstAddByContactId(int contactId) {
		//this.contactService.checkAccess(contactId);
		return this.sqlSession.selectOne("selectHistoryFirstAddByContactId", contactId);
	}	

	public void add(History history) {
		if(history.getAccountId() > 0) {
			//this.accountService.checkAccess(history.getAccountId());
		}
		if(history.getContactId() > 0) {
			//this.contactService.checkAccess(history.getContactId());
		}
		history.setUserId((int) sessionService.getSession().getUserId());
		history.setTimeStamp(new Timestamp(new Date().getTime()));
		int result =  this.sqlSession.insert("insertHistory", history);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

}