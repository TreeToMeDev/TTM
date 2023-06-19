package com.ttm.crm.server.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.OAuthAccessTokenSaveRequest;
import com.ttm.crm.server.entity.OAuthAuthorizationReply;
import com.ttm.crm.server.service.OAuthService;
import com.ttm.crm.server.service.SessionService;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

	private OAuthService oAuthService;
	private SessionService sessionService;
	
	public OAuthController(OAuthService oAuthService, SessionService sessionService) {
		this.oAuthService = oAuthService;
		this.sessionService = sessionService;
	}
	
	/* This is the FIRST OF MANY STEPS in the send-a-gmail-email process. It should
	 * be performed before giving a user access to send an email, to establish if
	 * we are setup with Google for that specific user.  The reply includes another 
	 * GET URL that the user can be redirected to to get a token that we can use to 
	 * get an access_token. If 'oauth_valid' is TRUE, then we can go ahead and accept
	 * the email without doing anything else.
	 */
	
	@GetMapping
	public ResponseEntity<OAuthAuthorizationReply> get(@RequestParam("redirect") String sourceUrl) {
		OAuthAuthorizationReply oAuthAuthorizationReply = oAuthService.get(sessionService.getSession().userId, sourceUrl);
		return ResponseEntity.ok().body(oAuthAuthorizationReply);
	}
	
	/*
	 * If the user is being newly authorized for Google, eg. the result of the above
	 * get() was oAuthValid = false, then the front end has to POST here to tell us
	 * what access code they got from Google (this is only available via a web process)
	 * and then we use it to get an access_token [etc] and save it in our DB. Once 
	 * we have that, we can finally send the email. 
	 */
	
	@PostMapping
	public void post(@RequestBody OAuthAccessTokenSaveRequest oAuthAccessTokenSaveRequest) {
		oAuthService.getToken(sessionService.getSession().userId, oAuthAccessTokenSaveRequest);
	}
		
}