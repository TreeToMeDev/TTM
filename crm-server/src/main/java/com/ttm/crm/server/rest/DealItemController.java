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

import com.ttm.crm.server.entity.DealItem;
import com.ttm.crm.server.service.DealItemService;

@RestController
@RequestMapping("/deal_items")
public class DealItemController {

	private DealItemService dealItemService;
	
	public DealItemController(DealItemService dealItemService) {
		this.dealItemService = dealItemService;
	}
	
	@GetMapping("{id}")
    public DealItem get(@PathVariable("id") int id) {
		return dealItemService.find(id);
	}
	
	@GetMapping("/deal/{dealId}")
    public List<DealItem> getByDealId(@PathVariable("dealId") int dealId) {
		return dealItemService.findByDealId(dealId);
	}
	
	@PostMapping
	public void post(@Valid @RequestBody DealItem dealItem) {
		dealItemService.add(dealItem);
	}
	
	@PatchMapping("/{id}")
    public ResponseEntity<DealItem> patch(@PathVariable("id") int id, @RequestBody DealItem dealItem) {
		dealItem.setId(id);
		dealItemService.update(dealItem);
		return null;
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
		dealItemService.delete(id);
		return ResponseEntity.ok().body(true);
	}
	
}
