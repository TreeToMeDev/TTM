package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.Email;
import com.ttm.crm.server.service.SessionService;

@Component
public class EmailDao {

	private SessionService sessionService;
	private final SqlSession sqlSession;
	
	public EmailDao(SqlSession sqlSession, SessionService sessionService) {
		this.sqlSession = sqlSession;
		this.sessionService = sessionService;
	}
	
	/*public List<Agent> findAll() {
		int userId = sessionService.getSession().getUserId();
		Agent agent = this.sqlSession.selectOne("selectAgentUser", userId);
		System.out.println("PARENT: " + agent.getParentId());
		if(agent.getParentId() == -1) {
			return this.sqlSession.selectList("selectAgentsAll", userId);
		} else {
			return this.sqlSession.selectList("selectAgentsFiltered", userId);
		}
	}
	
	public Agent find(int id) {
		Agent ret = this.sqlSession.selectOne("selectAgent", id);
		if(ret.getParentUserName() == null || ret.getParentUserName().equals("")) {
			ret.setParentUserName("None");
		}
		return ret;
	}*/
	
	public List<Email> findByContact(int contactId) {
		return this.sqlSession.selectList("selectEmailsByContact", contactId);
	}
	
	public void add(Email email) {
		int result = this.sqlSession.insert("insertEmail", email);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	/*public void update(Agent agent) {
		int result = this.sqlSession.update("updateAgent", agent);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void delete(int id) {
		int result = this.sqlSession.delete("deleteAgent", id);
		if(result != 1) {
			throw new RuntimeException();
		}
	}*/

}