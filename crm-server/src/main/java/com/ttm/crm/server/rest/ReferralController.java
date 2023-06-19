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

import com.ttm.crm.server.entity.Referral;
import com.ttm.crm.server.service.ReferralService;

@RestController
@RequestMapping("/referrals")
public class ReferralController {

	private ReferralService referralService;
	
	public ReferralController(ReferralService referralService) {
		this.referralService = referralService;
	}
	
	@GetMapping
    public List<Referral> get() {
		return referralService.findAllOpen();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Referral> get(@PathVariable("id") int id) {
		Referral referral = referralService.find(id);
		return ResponseEntity.ok().body(referral);
	}
	
	@PostMapping
	public void post(@Valid @RequestBody Referral referral) {
		referralService.add(referral);
	}
	
	@PostMapping("/{id}/convert")
	public void convertToContact(@PathVariable("id") int id) {
		referralService.convertToContact(id);
	}
	
	@PatchMapping("/{id}")
    public ResponseEntity<Referral> patch(@PathVariable("id") int id, @RequestBody Referral referral) {
		referral.setId(id);
		referralService.update(referral);
		return null;
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
		referralService.delete(id);
		return ResponseEntity.ok().body(true);
	}
}
