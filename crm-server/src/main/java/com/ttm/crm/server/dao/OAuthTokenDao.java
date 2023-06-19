package com.ttm.crm.server.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.OAuthToken;

@Component
public class OAuthTokenDao {

	private final SqlSession sqlSession;
	
	public OAuthTokenDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public OAuthToken findByUserId(long userId) {
		return this.sqlSession.selectOne("selectOAuthTokenByUser", userId);
	}
	
	public void add(OAuthToken oAuthToken) {
		int result = this.sqlSession.insert("insertOAuthToken", oAuthToken);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
	public void update(OAuthToken oAuthToken) {
		int result = this.sqlSession.update("updateOAuthToken", oAuthToken);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

}