package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.AccessChecker;
import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.service.AccountService;
import com.ttm.crm.server.service.SessionService;

@Component
public class ContactDao {

	private final AccessChecker accessChecker;
	private final SessionService sessionService;
	private final SqlSession sqlSession;
	
	public ContactDao(AccessChecker accessChecker, SessionService sessionService, SqlSession sqlSession) {
		this.accessChecker = accessChecker;
		this.sessionService = sessionService;
		this.sqlSession = sqlSession;
	}
	
	public List<Contact> find() {
		int userId = sessionService.getSession().getUserId();
		return this.sqlSession.selectList("selectAllContacts", userId);
	}
		
	public List<Contact> findByAccountId(int accountId) {
		return this.sqlSession.selectList("selectContactsByAccountId", accountId);
	}
	
	public Contact find(int id) {
		Contact ret = this.sqlSession.selectOne("selectContact", id);
		accessChecker.check(ret.getAgentId());
		return ret;
	}

	public int add(Contact contact) {
		accessChecker.check(contact.getAgentId());
		int result = this.sqlSession.insert("insertContact", contact);
		if(result != 1) {
			throw new RuntimeException();
		}
		return contact.getId();
	}
	
	public void update(Contact contact) {
		accessChecker.check(contact.getAgentId());
		int result = this.sqlSession.update("updateContact", contact);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
	public void delete(int id) {
		Contact contact = this.find(id);
		accessChecker.check(contact.getAgentId());
		int result = this.sqlSession.delete("deleteContact", id);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

}