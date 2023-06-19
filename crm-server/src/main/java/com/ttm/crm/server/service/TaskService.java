package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.Denullifier;
import com.ttm.crm.server.dao.HistoryDao;
import com.ttm.crm.server.dao.TaskDao;
import com.ttm.crm.server.entity.History;
import com.ttm.crm.server.entity.Task;
import com.ttm.crm.server.history.Differ;
import com.ttm.crm.server.history.HistoryAction;

@Service
public class TaskService {

	private Differ differ;
	private HistoryDao historyDao;
	private TaskDao taskDao;

	@Autowired
	public TaskService(Differ differ, HistoryDao historyDao, TaskDao taskDao) {
		this.differ = differ;
		this.historyDao = historyDao;
		this.taskDao = taskDao;
	}
	
	public void add(Task task) {
		Denullifier.denullify(task);
		taskDao.add(task);
		historyDao.add(new History(HistoryAction.ADD, task));
	}

	public void update(Task task) {
		Denullifier.denullify(task);
		Task oldTask = find((int) task.getId());
		differ.logDiffs(oldTask, task); 
		taskDao.update(task);
	}
	
	public List<Task> findAll() {
		return taskDao.findAll();
	}

	public List<Task> findAllOpen() {
		return taskDao.findAllOpen();
	}
	
	public List<Task> findAllByAccountId(int accountId) {
		return taskDao.findAllByAccountId(accountId);
	}

	public List<Task> findAllOpenByAccountId(int accountId) {
		return taskDao.findAllOpenByAccountId(accountId);
	}
	
	public List<Task> findAllByContactId(int contactId) {
		return taskDao.findAllByContactId(contactId);
	}

	public List<Task> findAllOpenByContactId(int contactId) {
		return taskDao.findAllOpenByContactId(contactId);
	}
	
	public Task find(int id) {
		return taskDao.find(id);
	}
	
	public void delete(int id) {
		Task task = find(id);
		taskDao.delete(id);
		historyDao.add(new History(HistoryAction.DELETE, task));
	}
	
	public void checkAccess(int id) {
		find(id);
	}
	
}
