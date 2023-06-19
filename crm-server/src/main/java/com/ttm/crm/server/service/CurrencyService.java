package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.dao.CurrencyDao;
import com.ttm.crm.server.entity.Currency;

@Service
public class CurrencyService {
	private CurrencyDao currencyDao;
	
	@Autowired
	public CurrencyService(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}
	
	public void add(Currency currency) {	
		currencyDao.add(currency);
	}

	public Currency find(String code) {
		return currencyDao.find(code);
	}

	public List<Currency> findAll() {
		return currencyDao.findAll();
	}

	public void delete(String code) {
		currencyDao.delete(code);
	}
}
