package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.Agent;
import com.ttm.crm.server.entity.User;
import com.ttm.crm.server.service.SessionService;

@Component
public class AgentDao {

	private SessionService sessionService;
	private final SqlSession sqlSession;
	
	public AgentDao(SqlSession sqlSession, SessionService sessionService) {
		this.sqlSession = sqlSession;
		this.sessionService = sessionService;
	}
	
	public List<Agent> findAll() {
		int userId = sessionService.getSession().getUserId();
		Agent agent = this.sqlSession.selectOne("selectAgentUser", userId);
		/* Special logic for agents, super users see everything, but otherwise filter.
		 * I'm not sure why this needs to work differently from everywhere else?
		 */
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
	}
	
	public Agent findByAuth0Id(String auth0Id) {
		return this.sqlSession.selectOne("selectAgentByAuth0Id", auth0Id);
	}
	
	public void add(Agent agent) {
		int result = this.sqlSession.insert("insertAgent", agent);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void update(Agent agent) {
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
	}

}