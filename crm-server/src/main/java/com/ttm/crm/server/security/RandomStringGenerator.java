package com.ttm.crm.server.security;

// http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string

import java.security.SecureRandom;

public class RandomStringGenerator {
	static final String AB = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
	static SecureRandom rnd = new SecureRandom();

	public static String get(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public static String get(int len, String chars) {
		char c = 0;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			// avoid sequential characters to conform to Auth0 rules (which allows max. of 2, we enforce only one)
			for (int j = 0; j < 99; j++) {
				c = chars.charAt(rnd.nextInt(chars.length()));
				if (i > 0 && sb.substring(i - 1, i).charAt(0) != c) {
					break;
				}
			}
			sb.append(c);
		}
		return sb.toString();
	}

	public static String getTricky(int len) {
		String ret = RandomStringGenerator.get(len, "ABCDEFGHOJKLMNOPQRSTUVWXYZ");
		ret += RandomStringGenerator.get(len, "abcdefghijklmnopqrstuvwxyz");
		ret += RandomStringGenerator.get(len, "0123456789");
		ret += RandomStringGenerator.get(len, "!@#$%^&*()");
		return ret;
	}

}
