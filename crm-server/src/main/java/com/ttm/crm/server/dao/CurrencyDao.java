package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.Currency;

@Component
public class CurrencyDao {
	private final SqlSession sqlSession;
	
	public CurrencyDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<Currency> findAll() {
		return this.sqlSession.selectList("selectAllCurrencies");
	}
	
	public Currency find(String code) {
		return this.sqlSession.selectOne("selectCurrency", code);
	}
	
	public void add(Currency currency) {
		int result = this.sqlSession.insert("insertCurrency", currency);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void delete(String code) {
		int result = this.sqlSession.delete("deleteCurrency", code);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
}
