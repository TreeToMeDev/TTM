package com.ttm.crm.server.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.Denullifier;
import com.ttm.crm.server.dao.ContactDao;
import com.ttm.crm.server.dao.HistoryDao;
import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.entity.History;
import com.ttm.crm.server.history.Differ;
import com.ttm.crm.server.history.HistoryAction;

@Service
public class ContactService {
	
	private ContactDao contactDao;
	private ContactTypeService contactTypeService;
	private Differ differ;
	private HistoryDao historyDao;

	@Autowired
	public ContactService(ContactDao contactDao, ContactTypeService contactTypeService, Differ differ, HistoryDao historyDao) {
		this.contactDao = contactDao;
		this.contactTypeService = contactTypeService;
		this.differ = differ;
		this.historyDao = historyDao;
	}
	
	@Transactional
	public List<Contact> findAllContacts() {
		List<Contact> contacts = contactDao.find();
		if (contacts == null) {
			throw new RuntimeException("Did not find any contacts");
		}
		for(Contact contact : contacts) {
			getHistory(contact);
		}
		return contacts;
	}
	
	@Transactional
	public List<Contact> findByAccountId (int accountId) {
		List<Contact> ret = contactDao.findByAccountId(accountId);
		for(Contact contact : ret) {
			getHistory(contact);
		}
		return ret;
	}

	@Transactional
	public Contact find(int id) {
		Contact contact = contactDao.find(id);
		if (contact == null) {
			throw new RuntimeException("Did not find customer contact - " + id);
		}
		getHistory(contact);
		return contact;
	}

	@Transactional
	public void add (Contact contact) {
		Denullifier.denullify(contact);
		int contactId = contactDao.add(contact);
		if (contact.getContactTypes() != null && contact.getContactTypes().size() > 0) {
			this.contactTypeService.addContactTypes(contactId, contact.getContactTypes());
		}
		
		historyDao.add(new History(HistoryAction.ADD, contact)); 
	}
	
	@Transactional
	public void update (Contact contact) {
		//Denullifier.denullify(customerContact);
		Contact oldContact = contactDao.find(contact.getId());
		differ.logDiffs(oldContact, contact);
		contactDao.update(contact);

		this.contactTypeService.updateContactTypesForContact(contact.getId(), contact.getContactTypes());
//		List<ContactTypeList> contactTypes = contact.getContactTypes();
		
//		if (contactTypes == null && oldContact.getContactTypes().size() > 0) {
//			// Delete All Contact Types for the Contact
//			this.contactTypeService.deleteAll(contact.getId(), contactTypes);
//		} else {
//			if (contactTypes != null) {
//				if (contact.getContactTypes().size() > 0) {
//					
//					for (int i = 0; i < contact.getContactTypes().size(); i++) {
//						ContactTypeList ctl = contact.getContactTypes().get(i);
//						System.out.println(ctl.getDescription());
//					}
//				};
//			}
//		}
		
	}
	
	public void delete(int id) {
		Contact oldContact = contactDao.find(id);
		contactDao.delete(id);
		historyDao.add(new History(HistoryAction.DELETE, oldContact));
	}
	
	public void checkAccess(int id) {
		this.find(id);
	}
	
	private void getHistory(Contact contact) {
		History history = historyDao.findLatestByContactId(contact.getId());
		if(history != null) {
			contact.setLastChangeAction(history.getAction());
			contact.setLastChangeDescription(history.getDescription());
			contact.setLastChangeTimestamp(history.getTimeStamp());
			contact.setLastChangeUserName(history.getUserName());
		}
		
		History addHistory = historyDao.findFirstAddByContactId(contact.getId());
		if(addHistory != null) {
			contact.setAddTimestamp(addHistory.getTimeStamp());
		}
	}
	
}
