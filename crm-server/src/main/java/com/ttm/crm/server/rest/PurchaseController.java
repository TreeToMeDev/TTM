package com.ttm.crm.server.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Purchase;
import com.ttm.crm.server.response.ActionResponse;
import com.ttm.crm.server.response.ActionStatus;
import com.ttm.crm.server.service.PurchaseService;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

	private PurchaseService purchaseService;
	
	public PurchaseController(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	@GetMapping("/account/{accountId}")
    public List<Purchase> getAccountPurchases(@PathVariable("accountId") int accountId) {
		List<Purchase> purchases = this.purchaseService.findByAccountId(accountId);
		return purchases;
    }
	
	@GetMapping("/{id}")
    public Purchase getPurchase(@PathVariable("id") int id) {
        Purchase purchase = this.purchaseService.findById(id);
    	return purchase;
    }
	
	@PostMapping
    public ResponseEntity<Purchase> newCustomerProduct(@RequestBody Purchase purchase) {
		this.purchaseService.add(purchase);
		return ResponseEntity.ok(purchase);
	}
	
	@PatchMapping
	public ActionResponse update(@RequestBody Purchase purchase) {
		this.purchaseService.update(purchase);
		return new ActionResponse(ActionStatus.SUCCESS);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
		purchaseService.delete(id);
		return ResponseEntity.ok().body(true);
	}

}
