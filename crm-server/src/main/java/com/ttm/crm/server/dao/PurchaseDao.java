package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.Purchase;
import com.ttm.crm.server.service.AccountService;

@Component
public class PurchaseDao {
	
	private final AccountService accountService;
	private final SqlSession sqlSession;
	
	public PurchaseDao(AccountService accountService, SqlSession sqlSession) {
		this.accountService = accountService;
		this.sqlSession = sqlSession;
	}
	
	public List<Purchase> findByAccountId(int accountId) {
		accountService.checkAccess(accountId);
		return this.sqlSession.selectList("selectPurchases", accountId);
	}
	
	public Purchase find(int id) {
		Purchase purchase = this.sqlSession.selectOne("selectPurchase", id);
		accountService.checkAccess(purchase.getAccountId());
		return purchase;
	}
	
	public void add(Purchase purchase) {
		accountService.checkAccess(purchase.getAccountId());
		int result = this.sqlSession.insert("insertPurchase", purchase);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
	public void update(Purchase purchase) {
		accountService.checkAccess(purchase.getAccountId());
		int result = this.sqlSession.update("updatePurchase", purchase);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
	public void delete(int id) {
		Purchase purchase = this.find(id);
		accountService.checkAccess(purchase.getAccountId());
		int result = this.sqlSession.delete("deletePurchase", id);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
}
