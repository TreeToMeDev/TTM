package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.dao.ContactTypeDao;
import com.ttm.crm.server.entity.ContactType;

@Service
public class ContactTypeService {

	private ContactTypeDao contactTypeDao;
	
	@Autowired
	public ContactTypeService(ContactTypeDao contactTypeDao) {
		this.contactTypeDao = contactTypeDao;
	}
	
	public void add(ContactType contactType) {	
		contactTypeDao.add(contactType);
	}

	//  Brand new Contact.  Assign contact ID from newly created Contact
	//  and Add it to the db
	public void addContactTypes(int contactId, List<ContactType> contactTypes) {
		for (int i = 0; i < contactTypes.size(); i++) {
			ContactType ct = contactTypes.get(i);
			ct.setContactId(contactId);
			add(ct);
		}
	}
	
	//  Existing Contact.  Compare the Contact Types to decide if any need to be
	//  added or deleted.
	public void updateContactTypesForContact(int contactId, List<ContactType> contactTypes) {	
		List<ContactType> oldContactTypes = findAllByContact(contactId);
		
		//  Look at existing Contact Types and compare against incoming list
		for (int i = 0; i < oldContactTypes.size(); i++) {
			ContactType oldContactType = oldContactTypes.get(i);
			ContactType c = contactTypes.stream().filter
					(ct -> ct.getContactTypeKey().equals(oldContactType.getContactTypeKey())).findFirst().orElse(null);
			
			//  Is there a match?  If not then delete from the Contact Type table
			if (c == null) {
				delete(contactId, oldContactType.getContactTypeKey());
			}
		}
		
		//  Now the reverse.  Look at the incoming list and compare to the original list
		for (int i = 0; i < contactTypes.size(); i++) {
			ContactType newContactType = contactTypes.get(i);
			ContactType oldC = oldContactTypes.stream().filter
					(oldCt -> oldCt.getContactTypeKey().equals(newContactType.getContactTypeKey())).findFirst().orElse(null);
			
			//  Is there a match?  If not then ADD to the Contact Type table
			if (oldC == null) {
				//  Set the contact ID first as it does not come back from the client.
				newContactType.setContactId(contactId);
				add(newContactType);
			}
		}
	}

	public ContactType find(int contactId, String contactTypeKey) {
		return contactTypeDao.find(contactId, contactTypeKey);
	}

	public List<ContactType> findAllByContact(int contactId) {
		return contactTypeDao.findAllByContact(contactId);
	}

	public void delete(int contactId, String contactTypeKey) {
		contactTypeDao.delete(contactId, contactTypeKey);
	}
	
	public void deleteAll(int contactId, List<ContactType> contactTypes) {
		for (int i = 0; i < contactTypes.size(); i++) {
			ContactType ct = contactTypes.get(i);
			contactTypeDao.delete(contactId, ct.getContactTypeKey());
		}
	}
}
