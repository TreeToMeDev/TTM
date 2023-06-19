package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.AccessChecker;
import com.ttm.crm.server.entity.Task;
import com.ttm.crm.server.service.SessionService;

@Component
public class TaskDao {

	private final AccessChecker accessChecker;
	private final SessionService sessionService;
	private final SqlSession sqlSession;
	
	public TaskDao(AccessChecker accessChecker, SessionService sessionService, SqlSession sqlSession) {
		this.accessChecker = accessChecker;
		this.sqlSession = sqlSession;
		this.sessionService = sessionService;
	}
	
	public List<Task> findAll() {
		int userId = sessionService.getSession().getUserId();
		return this.sqlSession.selectList("selectTasks", userId);
	}
	
	public List<Task> findAllOpen() {
		int userId = sessionService.getSession().getUserId();
		return this.sqlSession.selectList("selectOpenTasks", userId);
	}
	
	public List<Task> findAllByAccountId(int accountId) {
		//this.accountService.find(accountId); // does implicit access check
		return this.sqlSession.selectList("selectAccountTasks", accountId);
	}
	
	public List<Task> findAllOpenByAccountId(int accountId) {
		return this.sqlSession.selectList("selectAccountOpenTasks", accountId);
	}
	
	public List<Task> findAllByContactId(int contactId) {
		return this.sqlSession.selectList("selectContactTasks", contactId);
	}
	
	public List<Task> findAllOpenByContactId(int contactId) {
		return this.sqlSession.selectList("selectContactOpenTasks", contactId);
	}
	
	public Task find(int id) {
		Task ret =  this.sqlSession.selectOne("selectTask", id);
		accessChecker.check(ret.getAgentId());
		return ret;
	}
	
	public void add(Task task) {
		accessChecker.check(task.getAgentId());
		int result = this.sqlSession.insert("insertTask", task);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void update(Task task) {
		accessChecker.check(task.getAgentId());
		int result = this.sqlSession.update("updateTask", task);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
	public void delete (int id) {
		Task task = this.find(id);
		accessChecker.check(task.getAgentId());
		int result = this.sqlSession.delete("deleteTask", id);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
}