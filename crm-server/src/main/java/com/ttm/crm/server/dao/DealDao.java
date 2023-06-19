package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.AccessChecker;
import com.ttm.crm.server.entity.Deal;
import com.ttm.crm.server.entity.DealItem;
import com.ttm.crm.server.service.AccountService;
import com.ttm.crm.server.service.ContactService;
import com.ttm.crm.server.service.SessionService;

@Component
public class DealDao {

	private final AccessChecker accessChecker;
	private final AccountService accountService;
	private final ContactService contactService;
	private final DealItemDao dealItemDao;
	private final SqlSession sqlSession;
	private final SessionService sessionService;
	
	public DealDao(AccessChecker accessChecker, AccountService accountService, ContactService contactService, DealItemDao dealItemDao, SqlSession sqlSession, SessionService sessionService) {
		this.accessChecker = accessChecker;
		this.accountService = accountService;
		this.contactService = contactService;
		this.dealItemDao = dealItemDao;
		this.sessionService = sessionService;
		this.sqlSession = sqlSession;
	}
	
	public List<Deal> find() {
		int userId = sessionService.getSession().getUserId();
		return this.sqlSession.selectList("selectDeals", userId);
	}
	
	public List<Deal> findAllOpen() {
		int userId = sessionService.getSession().getUserId();
		return this.sqlSession.selectList("selectOpenDeals", userId);
	}
	
	public List<Deal> findAllByAccountId(int accountId) {
		this.accountService.find(accountId); // does implicit access check
		return this.sqlSession.selectList("selectAccountDeals", accountId);
	}
	
	public List<Deal> findAllOpenByAccountId(int accountId) {
		this.accountService.find(accountId); // does implicit access check
		return this.sqlSession.selectList("selectAccountOpenDeals", accountId);
	}
	
	public List<Deal> findAllByContactId(int contactId) {
		this.contactService.find(contactId); // does implicit access check
		return this.sqlSession.selectList("selectContactDeals", contactId);
	}
	
	public List<Deal> findAllOpenByContactId(int contactId) {
		this.contactService.find(contactId); // does implicit access check
		return this.sqlSession.selectList("selectContactOpenDeals", contactId);
	}
	
	public Deal find(int id) {
		Deal ret = this.sqlSession.selectOne("selectDeal", id);
		accessChecker.check(ret.getAgentId());
		return ret;
	}
	
	// special case, returns dealId for dealItem insert
	public int add(Deal deal) {
		accessChecker.check(deal.getAgentId());
		int result = this.sqlSession.insert("insertDeal", deal);
		if(result != 1) {
			throw new RuntimeException();
		}
		return deal.getId();
	}

	public void update(Deal deal) {
		accessChecker.check(deal.getAgentId());
		int result = this.sqlSession.update("updateDeal", deal);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void delete (int id) {
		Deal deal = this.find(id);
		accessChecker.check(deal.getAgentId());
		this.sqlSession.delete("deleteDeal", id);
		// TODO: error checking!
	}

	private List<Deal> getAmounts(List<Deal> deals) {
		for(Deal deal : deals) {
			List<DealItem> items = dealItemDao.findByDealId(deal.getId());
			for(DealItem dealItem : items) {
				deal.setAmount(getAmount(deal.getId()));
			}
		}
		return deals;
	}
	
	private long getAmount(int dealId) {
		List<DealItem> items = dealItemDao.findByDealId(dealId);
		long ret = 0;
		for(DealItem dealItem : items) {
			ret += ((long) (dealItem.getQuantity() * dealItem.getPrice()));
		}
		return ret;
	}
	
}