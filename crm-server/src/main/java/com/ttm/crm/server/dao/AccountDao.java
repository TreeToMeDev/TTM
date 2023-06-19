package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.AccessChecker;
import com.ttm.crm.server.entity.Account;
import com.ttm.crm.server.service.SessionService;

@Component
public class AccountDao {

	private final AccessChecker accessChecker;
	private final SessionService sessionService;
	private final SqlSession sqlSession;
	
	public AccountDao(AccessChecker accessChecker, SqlSession sqlSession, SessionService sessionService) {
		this.accessChecker = accessChecker;
		this.sessionService = sessionService;
		this.sqlSession = sqlSession;
	}
	
	public List<Account> findAll() {
		int userId = sessionService.getSession().getUserId();
		return this.sqlSession.selectList("selectAccounts", userId);
	}
	
	public Account find(int id) {
		Account ret = this.sqlSession.selectOne("selectAccount", id);
		accessChecker.check(ret.getAgentId());
		return ret;
	}

	public Account findByName(String name) {
		Account ret = this.sqlSession.selectOne("selectAccountByName", name);
		accessChecker.check(ret.getAgentId());
		return ret;
	}
	
	public void add(Account account) {
		accessChecker.check(account.getAgentId());
		int result = this.sqlSession.insert("insertAccount", account);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void update(Account account) {
		accessChecker.check(account.getAgentId());
		int result = this.sqlSession.update("updateAccount", account);
		if(result != 1) {
			throw new RuntimeException();
		}
	}


	public void delete(int id) {
		Account account = this.find(id);
		accessChecker.check(account.getAgentId());
		int result = this.sqlSession.delete("deleteAccount", id);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

}