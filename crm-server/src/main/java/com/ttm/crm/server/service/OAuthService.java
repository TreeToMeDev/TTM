package com.ttm.crm.server.service;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ttm.crm.server.dao.OAuthInfoDao;
import com.ttm.crm.server.dao.OAuthTokenDao;
import com.ttm.crm.server.entity.OAuthAccessTokenReply;
import com.ttm.crm.server.entity.OAuthAccessTokenSaveRequest;
import com.ttm.crm.server.entity.OAuthAuthorizationReply;
import com.ttm.crm.server.entity.OAuthInfo;
import com.ttm.crm.server.entity.OAuthToken;

@Service
public class OAuthService {

	private OAuthInfoDao oAuthInfoDao;
	private OAuthTokenDao oAuthTokenDao;

	@Autowired
	public OAuthService(OAuthInfoDao oAuthInfoDao, OAuthTokenDao oAuthTokenDao) {
		this.oAuthInfoDao = oAuthInfoDao;
		this.oAuthTokenDao = oAuthTokenDao;
	}
	
	public OAuthAuthorizationReply get(int userId, String sourceUrl) {
		OAuthToken oAuthToken = oAuthTokenDao.findByUserId(userId);
		OAuthAuthorizationReply ret = createReply(oAuthToken);
		if(ret.oAuthValid == false) {
			OAuthInfo oAuthInfo = oAuthInfoDao.findByVendor("GOOGLE");
			/* Front end can use this to redirect the user to a Google page that
			 * will generate an authorization code, which we get back in a redirect
			 * which the front end uses to call getToken.
			 */
			ret.oAuthGetAccessCodeUrl = MessageFormat.format( 
				"https://accounts.google.com/o/oauth2/auth?" +
				"response_type=code&" +
				"client_id={0}&" +
				/* This has to be filled in by the front end */
				"redirect_uri=FRONT_END_REDIRECT_URI&" +
				"scope=https://www.googleapis.com/auth/gmail.compose&" +
				"access_type=offline&" +
				"approval_prompt=force",
				oAuthInfo.clientId);
		}
		return ret;
	}
	
	private OAuthAuthorizationReply createReply(OAuthToken oAuthToken) {
		OAuthAuthorizationReply ret = new OAuthAuthorizationReply();
		ret.oAuthValid = false; // default;
		// will be true if user never granted us access
		if(oAuthToken == null) {
			return ret;
		}
		// these should never happen
		if(oAuthToken.accessToken == null || oAuthToken.accessToken.length() == 0
			|| oAuthToken.refreshToken == null || oAuthToken.refreshToken.length() == 0 
			|| oAuthToken.timeAcquired == null || oAuthToken.expiresIn <= 0) {
			System.out.println(MessageFormat.format("bad record in oauth_token for user {0}", oAuthToken.userId));
			return ret;
		}
		ret.oAuthValid = true;
		return ret;
	}
	
	public void getToken(int userId, OAuthAccessTokenSaveRequest oAuthAccessTokenSaveRequest) {
		try {
			int timeout = 30000;
			RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
			CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
			HttpPost httpPost = new HttpPost("https://oauth2.googleapis.com/token");
			String authCode = oAuthAccessTokenSaveRequest.redirectUrl.split("&")[0].split("=")[1];
			authCode = URLDecoder.decode(authCode, "UTF-8");
			List<NameValuePair> params = createRequest(authCode, oAuthAccessTokenSaveRequest.sourceUrl);
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String responseBody = entity != null ? EntityUtils.toString(entity) : null;
			System.out.println(responseBody);
			OAuthAccessTokenReply oAuthAccessTokenReply = new Gson().fromJson(responseBody, OAuthAccessTokenReply.class);
			if(oAuthAccessTokenReply.access_token == null || oAuthAccessTokenReply.access_token.length() == 0) {
				throw new RuntimeException();
			}
			saveTokens(userId, oAuthAccessTokenReply);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<NameValuePair> createRequest(String authorizationCode, String sourceUrl) {
		OAuthInfo oAuthInfo = oAuthInfoDao.findByVendor("GOOGLE");
		ArrayList<NameValuePair> ret = new ArrayList<NameValuePair>();
		ret.add(new BasicNameValuePair("code", authorizationCode));
		ret.add(new BasicNameValuePair("client_id", oAuthInfo.clientId));
		ret.add(new BasicNameValuePair("client_secret", oAuthInfo.clientSecret));
		ret.add(new BasicNameValuePair("redirect_uri", sourceUrl));
		ret.add(new BasicNameValuePair("grant_type", "authorization_code"));
		ret.add(new BasicNameValuePair("scope", "https://www.googleapis.com/auth/gmail.compose"));
		return ret;
	}
	
	private void saveTokens(int userId, OAuthAccessTokenReply oAuthAccessTokenReply) {
		OAuthToken oAuthToken = oAuthTokenDao.findByUserId(userId);
		// likely could use UPSERT here, probably, but not sure of Postgres details, so...
		boolean newToken = oAuthToken == null;
		if(newToken) {
			oAuthToken = new OAuthToken();
			oAuthToken.userId = userId;
			oAuthToken.vendor = "GOOGLE";
		}
		oAuthToken.accessToken = oAuthAccessTokenReply.access_token;
		oAuthToken.expiresIn = oAuthAccessTokenReply.expires_in;
		oAuthToken.refreshToken = oAuthAccessTokenReply.refresh_token;
		oAuthToken.timeAcquired = new Timestamp(System.currentTimeMillis());
		if(newToken) {
			oAuthTokenDao.add(oAuthToken);
		} else {
			oAuthTokenDao.update(oAuthToken);
		}
	}
	
}