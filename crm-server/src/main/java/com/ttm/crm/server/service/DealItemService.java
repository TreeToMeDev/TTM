package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.Denullifier;
import com.ttm.crm.server.dao.DealItemDao;
import com.ttm.crm.server.dao.HistoryDao;
import com.ttm.crm.server.entity.DealItem;
import com.ttm.crm.server.entity.History;
import com.ttm.crm.server.history.Differ;
import com.ttm.crm.server.history.HistoryAction;

@Service
public class DealItemService {

	private DealItemDao dealItemDao;
	private Differ differ;
	private HistoryDao historyDao;

	@Autowired
	public DealItemService(DealItemDao dealItemDao, Differ differ, HistoryDao historyDao) {
		this.dealItemDao = dealItemDao;
		this.differ = differ;
		this.historyDao = historyDao;
	}
	
	public void add(DealItem dealItem) {
		Denullifier.denullify(dealItem);
		dealItemDao.add(dealItem);
		historyDao.add(new History(HistoryAction.ADD, dealItem));
	}
	
	public void update(DealItem dealItem) {
		Denullifier.denullify(dealItem);
		DealItem oldDealItem = find((int) dealItem.getId());
		differ.logDiffs(oldDealItem, dealItem);
		dealItemDao.update(dealItem);
	}

	public DealItem find(int id) {
		return dealItemDao.find(id);
	}
	
	public List<DealItem> findByDealId(int dealId) {
		return dealItemDao.findByDealId(dealId);
	}

	public void delete(int id) {
		DealItem dealItem = find(id);
		dealItemDao.delete(id);
		historyDao.add(new History(HistoryAction.DELETE, dealItem));
	}
	
}
