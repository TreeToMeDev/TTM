package com.ttm.crm.server.rest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Session;
import com.ttm.crm.server.service.SessionService;

@RestController
@RequestMapping("/session")
public class SessionController {

	private SessionService sessionService;
	
	public SessionController(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
	// https://www.baeldung.com/spring-security-oauth-jwt
	@GetMapping
    public Session get(@AuthenticationPrincipal Jwt principal) {
		// get the Auth0 ID from the JWT
		String auth0Id = principal.getClaimAsString("sub");
		Session ret = sessionService.getByAuth0Id(auth0Id);
		return ret;
	}
	
}