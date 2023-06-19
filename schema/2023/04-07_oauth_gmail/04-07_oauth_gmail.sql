/* These records are created after the user is redirected to 
 * https://accounts.google.com/o/oauth2/auth?.
 * We save them via the front end POSTing to the 'oauth' endpoint.
 * This process generates an access code which can be used to get these tokens, 
 * which can in turn be used to access Google API's under the ID of the person
 * who did the call to https://accounts.google.com/o/oauth2/auth?. */

DROP TABLE IF EXISTS oauth_token;
DROP TABLE IF EXISTS oauth_info;

CREATE TABLE oauth_token (
	id SERIAL,
	user_id INTEGER,
	/* These can be used to access an OAuth-secured API. Must check for possible expiry. */
	access_token VARCHAR NOT NULL DEFAULT '',
	refresh_token VARCHAR NOT NULL DEFAULT '',
	/* This time (in server time) is when we did the call to Google to get this token, 
	 * we can add expires_in to this field to know when the access_token expires and 
	 * we have to get a new one using the refresh_token. access_token expires in a matter
	 * of minutes; refresh_token never expires, but we do have to consider the case
	 * of the user explicitly revoking permission via their Google account, which
	 * would render both tokens invalid, despite not having expired. */
	time_acquired TIMESTAMP WITH TIME ZONE,
	expires_in INTEGER,
	/* for now, just GOOGLE. Could be MICROSOFT someday. */
	vendor VARCHAR NOT NULL DEFAULT ''
);

CREATE INDEX ON oauth_token (vendor);

CREATE TABLE oauth_info (
	id SERIAL,
	client_id VARCHAR NOT NULL DEFAULT '', /* at Google, from API Console */
	client_secret VARCHAR NOT NULL DEFAULT '', /* at Google, from API Console */ 
	vendor VARCHAR /* eg. GOOGLE but in theory could be anyone that supports OAuth */
);

CREATE INDEX ON oauth_info (vendor);

/* From Google API console, eg. https://console.cloud.google.com/apis/credentials?project=wise-sandbox-378121
 * under GT's Google login (TODO: needs to be upgraded to production status, currently only works
 * for a small list of authorized Google logins, can be made to work with any ID) */

INSERT INTO oauth_info (client_id, client_secret, vendor) VALUES (
	'777547823297-ggodjvndideq2b6vpfmj93vbbo27c21o.apps.googleusercontent.com',
	'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',
	'GOOGLE'
);