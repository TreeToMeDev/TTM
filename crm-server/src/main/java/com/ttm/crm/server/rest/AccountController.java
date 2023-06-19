package com.ttm.crm.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Account;
import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping
    public List<Account> get() {
		return accountService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Account> get(@PathVariable("id") int id) {
		Account account = accountService.find(id);
		return ResponseEntity.ok().body(account);
	}
	
	@PostMapping
	public void post(@Valid @RequestBody Account account) {
		accountService.add(account);
	}
	
	@PatchMapping("/{id}")
    public ResponseEntity<Contact> patch(@PathVariable("id") int id, @Valid @RequestBody Account account) {
		account.setId(id);
		accountService.update(account);
		return null;
	}

	@DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
		accountService.delete(id);
		return ResponseEntity.ok().body(true);
	}
	
}
