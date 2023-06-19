package com.ttm.crm.server.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.AppConfig;

@Component
public class AppConfigDao {

	private final SqlSession sqlSession;
	
	public AppConfigDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<AppConfig> findAllByCategory(String category) {
		List<AppConfig> configs = this.sqlSession.selectList("selectAppConfigByCategory", category);
		return configs;
	}
	
	public AppConfig find(String category, String configOption) {
		HashMap<String, Object> values = new HashMap<String, Object>();
	    values.put("category", category);
	    values.put("configOption", configOption);
	    AppConfig config = this.sqlSession.selectOne("selectAppConfig", values);
		return config;
	}
}
