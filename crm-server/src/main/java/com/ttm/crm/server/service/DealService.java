package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.Denullifier;
import com.ttm.crm.server.dao.DealDao;
import com.ttm.crm.server.dao.HistoryDao;
import com.ttm.crm.server.entity.Deal;
import com.ttm.crm.server.entity.History;
import com.ttm.crm.server.history.Differ;
import com.ttm.crm.server.history.HistoryAction;

@Service
public class DealService {

	private DealDao dealDao;
	private Differ differ;
	private HistoryDao historyDao;

	@Autowired
	public DealService(DealDao dealDao, Differ differ, HistoryDao historyDao) {
		this.dealDao = dealDao;
		this.differ = differ;
		this.historyDao = historyDao;
	}
	
	public int  add(Deal deal) {
		Denullifier.denullify(deal);
		historyDao.add(new History(HistoryAction.ADD, deal));
		return dealDao.add(deal);
	}

	public void update(Deal deal) {
		Denullifier.denullify(deal);
		Deal oldDeal = find((int) deal.getId());
		differ.logDiffs(oldDeal, deal); 
		dealDao.update(deal);
	}
	
	public List<Deal> find() {
		return dealDao.find();
	}
	
	public List<Deal> findAllOpen() {
		return dealDao.findAllOpen();
	}
	
	public List<Deal> findAllByAccountId(int accountId) {
		return dealDao.findAllByAccountId(accountId);
	}

	public List<Deal> findAllOpenByAccountId(int accountId) {
		return dealDao.findAllOpenByAccountId(accountId);
	}
	
	public List<Deal> findAllByContactId(int contactId) {
		return dealDao.findAllByContactId(contactId);
	}

	public List<Deal> findAllOpenByContactId(int contactId) {
		return dealDao.findAllOpenByContactId(contactId);
	}
	
	public Deal find(int id) {
		return dealDao.find(id);
	}
	
	public void delete(int id) {
		Deal deal = find(id);
		dealDao.delete(id);
		historyDao.add(new History(HistoryAction.DELETE, deal));
	}
	
	public void checkAccess(int id) {
		find(id);
	}
	
}
