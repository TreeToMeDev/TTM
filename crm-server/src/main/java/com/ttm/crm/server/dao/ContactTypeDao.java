package com.ttm.crm.server.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.ContactType;
import com.ttm.crm.server.service.ContactService;

@Component
public class ContactTypeDao {

	//private final ContactService contactService;
	private final SqlSession sqlSession;
	
	public ContactTypeDao(/*ContactService contactService,*/ SqlSession sqlSession) {
		//this.contactService = contactService;
		this.sqlSession = sqlSession;
	}
	
	public List<ContactType> findAllByContact(int contactId) {
		return this.sqlSession.selectList("selectContactTypesByContact", contactId);
	}

	public ContactType find(int contactId, String contactTypeKey) {
		//contactService.checkAccess(contactId);
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("contactId", contactId);
		values.put("contactTypeKey", contactTypeKey);
		return this.sqlSession.selectOne("selectContactType", values);
	}
	
	public int add(ContactType contactType) {
		int result = this.sqlSession.insert("insertContactType", contactType);
		if(result != 1) {
			throw new RuntimeException();
		}
		return contactType.getContactId();
	}

	public void delete(int contactId, String contactTypeKey) {
		//contactService.checkAccess(contactId);
		HashMap<String, Object> values = new HashMap<String, Object>();
	    values.put("contactId", contactId);
	    values.put("contactTypeKey", contactTypeKey);
	    int result = this.sqlSession.delete("deleteContactType", values);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
}