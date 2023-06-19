// Based on Onyen's Auth0Wrapper but does not include JWTVerifier, as that is done
// by Spring. So for Syker, this is about communicating with Auth0's API for the purpose
// of adding new users (and maybe other stuff in the future).

package com.ttm.crm.server.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

final class Auth0AccessTokenResponse {
	public int expires_in;
	public String access_token;
	public String scope;
	public String token_type;
}

final class Auth0UserIdList {
	public String user_id;
	public String email;
}

public class Auth0 {

	static String auth0Prefix = "https://treetome.us.auth0.com/api/v2/";
	static String auth0Token  = "https://treetome.us.auth0.com/oauth/token";

	public static String addUser(String userEmail, String password) throws IOException {
		String resp = sendToAuth0("POST", "", "{\"connection\": \"Username-Password-Authentication\",\"email\": \"" + userEmail + "\", \"password\": \"" + password + "\"}");
		Gson gson = new GsonBuilder().create();
		Auth0UserIdList idrec = gson.fromJson(resp, Auth0UserIdList.class);
		String id = idrec.user_id;
		if (id == null || id.length() < 5) {
			System.out.println(resp);
			throw new RuntimeException("auth0 ID malformed: " + id);
		}
		return id;
	}
	
	private static String sendToAuth0(String verb, String urlSuffix, String requestBody) throws IOException {
		// https://manage.auth0.com/dashboard/us/onyen/applications/WZwdEXcwBUz06AQBGTgKPCrZ1ao3yz0A/quickstart
		// curl --request GET --url https://onyen.auth0.com/api/v2/users/auth0|5f667920c85b39006f51db6c?fields=email&include_fields=true --header 'authorization: Bearer
		// eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik5VVkZRMEpDUlRkQ01FWkZNalZEUXpsR05EQkVPRU0zUmtZMk1ETXhRakU0T1VWRU1EVTRSQSJ9.eyJpc3MiOiJodHRwczovL29ueWVuLmF1dGgwLmNvbS8iLCJzdWIiOiJXWndkRVhjd0JVejA2QVFCR1RnS1BDcloxYW8zeXowQUBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9vbnllbi5hdXRoMC5jb20vYXBpL3YyLyIsImlhdCI6MTYyMjgxMzQ1MSwiZXhwIjoxNjIyODk5ODUxLCJhenAiOiJXWndkRVhjd0JVejA2QVFCR1RnS1BDcloxYW8zeXowQSIsInNjb3BlIjoicmVhZDp1c2VycyB1cGRhdGU6dXNlcnMgZGVsZXRlOnVzZXJzIGNyZWF0ZTp1c2VycyIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.chOvC5qHb7KWVu9sd6G3IHxFoTfmSgXOfEFhl1VamjGqhlgpkwQ7ZIsjSaAfPGhmIWAWoPESiqrFbA1OR4VQQmqV2VETvzgT18YnrvvCra-ZHzkYEwC3rWfaqmpAeCVlFVmnk-E-r8XlIWTZGrv3ujcyHkLvajhvp1V6_KbuArvLya-FhE3fTroenrs6eFTwgZLLhbErA_Hdl4PTP9VTyC9zTj0zYAQcixc_Dg5Zig0j8QJNNu7qkZPn8odbrLFd7rXB9cGTa3q6oFwIfFWH1zIpptvhyP3Dok6pfK6lfRcgvcbbxe45bf6w4i4MhhgOQV74NqdavSOiYjjfeWaYBg'
		String auth0Token = "Bearer " + getAdminApplicationAccessToken();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			String responseBody;
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				// do not understand why an IMPORT doesn't work here or how it compiles without an IMPORT (on HttpResponse)
				public String handleResponse(final org.apache.http.HttpResponse response) throws ClientProtocolException, IOException {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
			};
			//if (verb.equals("GET")) {
				//HttpGet httpGet = new HttpGet("https://onyen.auth0.com/api/v2/users" + urlSuffix);
				//httpGet.setHeader("Authorization", auth0Token);
				//responseBody = httpclient.execute(httpGet, responseHandler);
			//} else if (verb.equals("DELETE")) {
				//HttpDelete httpDelete = new HttpDelete("https://onyen.auth0.com/api/v2/users" + urlSuffix);
				//httpDelete.setHeader("Authorization", auth0Token);
				//responseBody = httpclient.execute(httpDelete, responseHandler);
			//} else {
				HttpPost httpPost = new HttpPost(auth0Prefix + "users");
				StringEntity str = new StringEntity(requestBody);
				str.setContentType("application/json");
				httpPost.setEntity(str);
				httpPost.setHeader("Authorization", auth0Token);
				responseBody = httpclient.execute(httpPost, responseHandler);
			//}
			return responseBody;
		} finally {
			httpclient.close();
		}
	}
	
	// For Canchek, we do not do this: we have a hardcoded Access Token that always works, years later. The token
	// for Onyen has an expiry time in it (default 36000 seconds, could be increased). The simplest and safest thing
	// seems to be, just get a new token every time. However, it disturbs me that I don't understand why Onyen is
	// different from Canchek!
	//
	// By the way, to make this work, one must setup a new 'Application' in Auth0, with a type 'Machine to Machine'.
	// The client ID and secret for this is different from the one that normal users use.
	public static String getAdminApplicationAccessToken() {
		// curl --request POST
		// --url "https://onyen.auth0.com/oauth/token"
		// --header "content-type: application/x-www-form-urlencoded"
		// --data grant_type=client_credentials
		// --data client_id=WZwdEXcwBUz06AQBGTgKPCrZ1ao3yz0A
		// --data client_secret=pR1DG0VroprRdbR9cbOKTwex7xjdhiDzUwlFTN3dMkothtpYUYXSPAU-MpCuWctm
		// --data audience=https://onyen.auth0.com/api/v2/
		try {
			CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(auth0Token);
			httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
			// for Syker, had to change to BasicNameValuePair, I don't understand WHY
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("audience", auth0Prefix));
			params.add(new BasicNameValuePair("client_id", "CiKhoDWJ8Q0KVrz9wR0xSbembh2yCjiV"));
			params.add(new BasicNameValuePair("client_secret", "34uacbsgW9Gu3pJu54ywLr7q4yz9Wd8BBIlJVmYY5TTI9Bg4P2Dy_fyZqjwT6FTW"));
			params.add(new BasicNameValuePair("grant_type", "client_credentials"));
			// 34uacbsgW9Gu3pJu54ywLr7q4yz9Wd8BBIlJVmYY5TTI9Bg4P2Dy_fyZqjwT6FTW
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = closeableHttpResponse.getEntity();
			String response = httpEntity != null ? EntityUtils.toString(httpEntity) : null;
			closeableHttpClient.close();
			Auth0AccessTokenResponse auth0AccessTokenResponse = new Gson().fromJson(response, Auth0AccessTokenResponse.class);
			return auth0AccessTokenResponse.access_token;
		} catch (Exception e) {
			System.out.println("EXCEPTION " + e + e.getMessage());
			return null;
		}
	}

	// for testing only !
	public static void main(String[] args) {
		try {
			addUser("test3@treetome.ca", RandomStringGenerator.getTricky(3));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
		
}
