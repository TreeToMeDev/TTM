package com.ttm.crm.server;

import java.text.MessageFormat;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.Agent;
import com.ttm.crm.server.service.SessionService;

@Component
public class AccessChecker {

	private SessionService sessionService;
	private final SqlSession sqlSession;
	
	public AccessChecker(SessionService sessionService, SqlSession sqlSession) {
		this.sessionService = sessionService;
		this.sqlSession = sqlSession;
	}
	
	public void check(long idOfRecordOwner) {
		int userId = sessionService.getSession().getUserId();
		Agent agent = this.sqlSession.selectOne("selectAgentUser", userId);
		if(agent.getParentId() == -1) {
			return;
		} else {
			List<Integer> ids = this.sqlSession.selectList("getAccessibleIds", userId);
			for(Integer i : ids) {
				// equals() does NOT work here with int vs long
				if(i == idOfRecordOwner) {
					return;
				}
			}
		}
		throw new RuntimeException(MessageFormat.format("access violation: User {0} attempted to access a record belonging to {1}", userId, idOfRecordOwner));
	}
	
}