package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.Denullifier;
import com.ttm.crm.server.dao.AccountDao;
import com.ttm.crm.server.dao.HistoryDao;
import com.ttm.crm.server.entity.Account;
import com.ttm.crm.server.entity.History;
import com.ttm.crm.server.history.Differ;
import com.ttm.crm.server.history.HistoryAction;

@Primary
@Service
public class AccountService {

	private AccountDao accountDao;
	private Differ differ;
	private HistoryDao historyDao;

	@Autowired
	public AccountService(AccountDao accountDao, Differ differ, HistoryDao historyDao) {
		this.accountDao = accountDao;
		this.differ = differ;
		this.historyDao = historyDao;
	}
	
	public void add(Account account) {
		Denullifier.denullify(account);
		accountDao.add(account);
		historyDao.add(new History(HistoryAction.ADD, account)); 
	}
	
	public void update(Account account) {
		Denullifier.denullify(account);
		Account oldAccount = find((int) account.getId());
		differ.logDiffs(oldAccount, account); 
		accountDao.update(account);
	}
	
	public List<Account> findAll() {
		List<Account> ret = accountDao.findAll();
		for(Account account : ret) {
			getHistory(account);
		}
		return ret;
	}

	public Account find(int id) {
		Account account = accountDao.find(id);
		getHistory(account);
		return account;
	}
	
	public Account findByName(String name) {
		return this.accountDao.findByName(name);
	}
	
	public void delete(int id) {
		Account account = find(id);
		historyDao.add(new History(HistoryAction.DELETE, account));
		accountDao.delete(id);
	}
	
	public void checkAccess(int id) {
		this.find(id);
	}
	
	private void getHistory(Account account) {
		History history = historyDao.findLatestByAccountId(account.getId());
		if(history != null) {
			account.setLastChangeAction(history.getAction());
			account.setLastChangeDescription(history.getDescription());
			account.setLastChangeTimestamp(history.getTimeStamp());
			account.setLastChangeUserName(history.getUserName());
		}
	}
	
}
