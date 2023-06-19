package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.dao.ContactTypeListDao;
import com.ttm.crm.server.entity.ContactTypeList;

@Service
public class ContactTypeListService {
	
	private ContactTypeListDao contactTypeListDao;
	
	@Autowired
	public ContactTypeListService(ContactTypeListDao contactTypeListDao) {
		this.contactTypeListDao = contactTypeListDao;
	}
	
	public void add(ContactTypeList contactTypeList) {	
		contactTypeListDao.add(contactTypeList);
	}

	public ContactTypeList find(String description) {
		return contactTypeListDao.find(description);
	}

	public List<ContactTypeList> find() {
		return contactTypeListDao.find();
	}

	public void delete(String description) {
		contactTypeListDao.delete(description);
	}
}
