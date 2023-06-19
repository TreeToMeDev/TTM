package com.ttm.crm.server.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Referral;
import com.ttm.crm.server.service.ReferralService;

@RestController
@RequestMapping("/referralform")
public class ReferralFormController {

private ReferralService referralService;
	
	public ReferralFormController(ReferralService referralService) {
		this.referralService = referralService;
	}
	
	@PostMapping
	public void post(@RequestBody Referral referral) {
		referralService.add(referral);
	}
}
