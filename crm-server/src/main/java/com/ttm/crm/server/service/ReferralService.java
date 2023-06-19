package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.Denullifier;
import com.ttm.crm.server.dao.HistoryDao;
import com.ttm.crm.server.dao.NotesDao;
import com.ttm.crm.server.dao.ReferralDao;
import com.ttm.crm.server.dao.UserDao;
import com.ttm.crm.server.entity.Account;
import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.entity.Referral;
import com.ttm.crm.server.entity.User;
import com.ttm.crm.server.history.Differ;

@Service
public class ReferralService {
	private ReferralDao referralDao;
	private AccountService accountService;
	private ContactService contactService;
	private UserDao userDao;
	private NotesDao notesDao;
	private Differ differ;
	
	@Autowired
	public ReferralService(ReferralDao referralDao, 
						   AccountService accountService, 
						   ContactService contactService,
						   UserDao userDao,
						   NotesDao notesDao,
						   HistoryDao historyDao,
						   Differ differ) {
		this.referralDao = referralDao;
		this.accountService = accountService;
		this.contactService = contactService;
		this.userDao = userDao;
		this.notesDao = notesDao;
		this.differ = differ;
	}
	
	public void add(Referral referral) {
		Denullifier.denullify(referral);
		referralDao.add(referral);
	}

	public void update(Referral referral) {
		Denullifier.denullify(referral);
		Referral oldReferral = find((int) referral.getId());
		differ.logDiffs(oldReferral, referral); 
		referralDao.update(referral);
	}
	
	public List<Referral> find() {
		return referralDao.find();
	}
	
	public Referral find(int id) {
		return referralDao.find(id);
	}
	
	public List<Referral> findAllOpen() {
		return referralDao.findAllOpen();
	}
	
	public void delete(int id) {
		referralDao.delete(id);
	}
	
	public boolean convertToContact(int id) {
	
		Referral r = this.referralDao.find(id);
		
		if (r != null) {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentUserId = authentication.getName();
			User user = this.userDao.findByAuth0Id(currentUserId);
			
			Contact newContact = new Contact();
			
			Account newAccount = new Account();
			newAccount.setName(r.getCompanyName());
			newAccount.setAgentId(user.getId());
			newAccount.setAccountType("Prospect");
			newAccount.setBillingCity("");
		
			this.accountService.add(newAccount);
			newContact.setAccountId(newAccount.getId());
			
			newContact.setFirstName(r.getFirstName());
			newContact.setLastName(r.getLastName());
			newContact.setEmail(r.getEmail());
			newContact.setPhone(r.getPhone());
			newContact.setTitle(r.getJobTitle());
			newContact.setAgentId(user.getId());
			
			newContact.setSource("REFERRAL");
			newContact.setReferralId(r.getId());
			
			this.contactService.add(newContact);
			
			r.setContactId(newContact.getId());
	
			this.notesDao.convertReferralNotes(r.getId(), newContact.getId(), newAccount.getId());
			
			this.referralDao.update(r);
			
			return true;
		}
		
		return false;
	}
}
