package com.ttm.crm.server.entity;

/* Returns the URL that a user can be redirected to to get a Google access code.
 * This only happens the first time the user grants us access, it does NOT happen
 * every time we use Google's API.
 */

public class OAuthAuthorizationReply {
	public boolean oAuthValid;
	public String oAuthGetAccessCodeUrl;
}
