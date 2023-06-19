package com.ttm.crm.server.entity;

/* This is what we get back when we send an authorization code to Google and
 * they send us back an access token. This happens a lot - not just the first 
 * time the user grants us access to their Google account.
 */

public class OAuthAccessTokenReply {
	/* underscores because that's what Google send us and AFAIK we can't
	 * map this in GSON and I am too lazy to lookup the @annotation to
	 * deal with this properly.
	 */
	public int expires_in;
	public String access_token;
	public String refresh_token;
	public String scope;
	public String token_type;
}