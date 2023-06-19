package com.ttm.crm.server.service;

import static javax.mail.Message.RecipientType.TO;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.ttm.crm.server.dao.EmailDao;
import com.ttm.crm.server.dao.OAuthInfoDao;
import com.ttm.crm.server.dao.OAuthTokenDao;
import com.ttm.crm.server.entity.Email;
import com.ttm.crm.server.entity.Note;
import com.ttm.crm.server.entity.OAuthInfo;
import com.ttm.crm.server.entity.OAuthToken;
import com.ttm.crm.server.entity.User;

@Service
public class EmailService {

	private final EmailDao emailDao;
	private Gmail gmail = null;
	private final OAuthInfo oAuthInfo;
	private final OAuthTokenDao oAuthTokenDao;
	private final SessionService sessionService;
	private final UserService userService;
	
	@Autowired
	public EmailService(EmailDao emailDao, OAuthInfoDao oAuthInfoDao, OAuthTokenDao oAuthTokenDao, SessionService sessionService, UserService userService) {
		this.emailDao = emailDao;
		this.oAuthInfo = oAuthInfoDao.findByVendor("GOOGLE");
		this.oAuthTokenDao = oAuthTokenDao;
		this.sessionService = sessionService;
		this.userService = userService;
	}
	
	public List<Email> findByContact(int contactId) {
		return emailDao.findByContact(contactId);
	}
	
	private Credential getCredentials(NetHttpTransport netHttpTransport, GsonFactory gsonFactory) {
		/* GoogleCredential is deprecated however I couldn't get any of the
		 * alternatives (eg. GoogleCredentials, plural) to work. All of them depended
		 * on things I don't have, or, assume that we can prompt the user directly
		 * from here (which is obviously not true in a web server environment).
		 */
		GoogleCredential googleCredential;
		try {
			int userId = sessionService.getSession().getUserId();
			OAuthToken oAuthToken = oAuthTokenDao.findByUserId(userId);
			googleCredential = new GoogleCredential.Builder()
				.setClientSecrets(oAuthInfo.clientId, oAuthInfo.clientSecret)
				.setJsonFactory(gsonFactory)
				.setTransport(netHttpTransport)
				.build();
			/* PROBLEM SCENARIO 1:
			 * - user has oauth_token record
			 * - user enters Email module on TTM, we check for oauth_token, all is well
			 * - we delete the oauth_token record while user is still in Email module
			 * - user tries to send email, this method runs, the oAuthTokenDao.findByUserId call returns null
			 * - result: when they send email, they get an NPE on the next line */
			/* PROBLEM SCENARIO 2:
			 * - appears that if token is way past expiry, we don't handle it properly
			 */
			System.out.println("CHECK TOKEN EXPIRED?");
			System.out.println(oAuthToken.timeAcquired.getTime());
			System.out.println(oAuthToken.expiresIn * 1000);
			System.out.println(oAuthToken.timeAcquired.getTime() + (oAuthToken.expiresIn * 1000));
			System.out.println(System.currentTimeMillis());
			if(oAuthToken.timeAcquired.getTime() + (oAuthToken.expiresIn * 1000) > System.currentTimeMillis()) {
				System.out.println("TOKEN OK");
				googleCredential.setAccessToken(oAuthToken.accessToken);
			} else {
				System.out.println("REFRESH");
				// TODO: should resultant refresh be saved in oAuthToken?
				// SPECULATION: error happenswhen refreshToken() itself expires
				googleCredential.setRefreshToken(oAuthToken.refreshToken);
				googleCredential.refreshToken();
			}	
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		return googleCredential;
	}
	
	public void send(String body, String recipient, String subject) {
		try {
			getGmailObject();
	        Properties properties = new Properties();
	        Session session = Session.getDefaultInstance(properties, null);
	        MimeMessage email = new MimeMessage(session);
	        email.setFrom(new InternetAddress(getLoggedInUserEmail()));
	        email.setText(body);
	        email.addRecipient(TO, new InternetAddress(recipient));
	        email.setSubject(subject);
	        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	        email.writeTo(buffer);
	        byte[] rawMessageBytes = buffer.toByteArray();
	        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
	        Message message = new Message();
	        message.setRaw(encodedEmail);
            message = gmail.users().messages().send("me", message).execute();
            save(body, recipient, subject);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void save(String body, String recipient, String subject) {
		System.out.println(recipient);
		if(subject == null) {
			subject = "";
		}
		Email email = new Email();
		email.setBody(body);
		// TODO TODO TODO
		email.setContactId(-1);
		email.setRecipient(recipient);
		email.setSubject(subject);
        email.setTimeStamp(new Timestamp(new Date().getTime()));
        int userId = sessionService.getSession().getUserId();
        email.setUserId(userId);
        emailDao.add(email);
	}
	
	private void getGmailObject() {
		try {
			NetHttpTransport netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
	        GsonFactory gsonFactory = GsonFactory.getDefaultInstance();
	        gmail = new Gmail.Builder(netHttpTransport, gsonFactory, getCredentials(netHttpTransport, gsonFactory))
	                .setApplicationName("Test Mailer")
	                .build();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getLoggedInUserEmail() {
		int userId = sessionService.getSession().getUserId();
		User user = userService.find(userId);
		return user.getEmail();
	}
	
}