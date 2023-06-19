package com.ttm.crm.server.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.dao.HistoryDao;
import com.ttm.crm.server.entity.History;

@Service
public class HistoryService {
	
	private HistoryDao historyDao;

	@Autowired
	public HistoryService(HistoryDao historyDao) {
		this.historyDao = historyDao;
	}
	
	@Transactional
	public List<History> fetchByAccountId(int accountId) {
		List<History> ret = historyDao.fetchByAccountId(accountId);
		return ret;
	}

	@Transactional
	public List<History> fetchByContactId(int contactId) {
		List<History> ret = historyDao.fetchByContactId(contactId);
		return ret;
	}

	@Transactional
	public List<History> fetchByDealId(int dealId) {
		List<History> ret = historyDao.fetchByDealId(dealId);
		return ret;
	}

	@Transactional
	public List<History> fetchByTaskId(int taskId) {
		List<History> ret = historyDao.fetchByTaskId(taskId);
		return ret;
	}
	
}
