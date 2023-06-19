package com.ttm.crm.server.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Currency;
import com.ttm.crm.server.service.CurrencyService;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

	private CurrencyService currencyService;
	
	public CurrencyController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@GetMapping()
    public List<Currency> getAllCurrencies() {
		return this.currencyService.findAll();
	}
}
