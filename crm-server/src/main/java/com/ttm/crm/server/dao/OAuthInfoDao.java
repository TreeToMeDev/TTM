package com.ttm.crm.server.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.OAuthInfo;

@Component
public class OAuthInfoDao {

	private final SqlSession sqlSession;
	
	public OAuthInfoDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public OAuthInfo findByVendor(String vendor) {
		return this.sqlSession.selectOne("selectOAuthInfoByVendor", vendor);
	}

}