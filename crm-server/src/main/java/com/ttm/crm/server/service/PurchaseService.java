package com.ttm.crm.server.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.dao.HistoryDao;
import com.ttm.crm.server.dao.PurchaseDao;
import com.ttm.crm.server.entity.History;
import com.ttm.crm.server.entity.Purchase;
import com.ttm.crm.server.history.Differ;
import com.ttm.crm.server.history.HistoryAction;

@Service
public class PurchaseService {
	
	private Differ differ;
	private HistoryDao historyDao;
	private PurchaseDao purchaseDao;

	@Autowired
	public PurchaseService(Differ differ, HistoryDao historyDao, PurchaseDao purchaseDao) {
		this.differ = differ;
		this.historyDao = historyDao;
		this.purchaseDao = purchaseDao;
	}
	
	@Transactional
	public List<Purchase> findByAccountId(int accountId) {
		return purchaseDao.findByAccountId(accountId);
	}

	@Transactional
	public Purchase findById(int theId) {
		Purchase purchase = purchaseDao.find(theId);
		if (purchase == null) {
			throw new RuntimeException("Did not find customer Product - " + theId);
		}
		return purchase;
	}

	public void add (Purchase purchase) {
		purchaseDao.add(purchase);
		historyDao.add(new History(HistoryAction.ADD, purchase));
	}
	
	public void update (Purchase purchase) {
		Purchase oldPurchase = findById(purchase.getId());
		purchaseDao.update(purchase);
		differ.logDiffs(oldPurchase, purchase); 
	}
	
	public void delete(int id) {
		Purchase purchase = findById(id);
		purchaseDao.delete(id);
		historyDao.add(new History(HistoryAction.DELETE, purchase));
	}
}
