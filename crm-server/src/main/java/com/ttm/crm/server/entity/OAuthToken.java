package com.ttm.crm.server.entity;

import java.sql.Timestamp;

public class OAuthToken {
	public int userId;
	public long expiresIn;
	public long id;
	public String accessToken;
	public String refreshToken;
	public String vendor;
	public Timestamp timeAcquired;
}
