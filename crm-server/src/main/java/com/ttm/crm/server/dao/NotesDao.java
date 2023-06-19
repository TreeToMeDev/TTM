package com.ttm.crm.server.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.Note;
import com.ttm.crm.server.service.AccountService;
import com.ttm.crm.server.service.ContactService;

@Component
public class NotesDao {

	private final AccountService accountService;
	private final ContactService contactService;
	private final SqlSession sqlSession;
	
	public NotesDao(AccountService accountService, ContactService contactService, SqlSession sqlSession) {
		this.accountService = accountService;
		this.contactService = contactService;
		this.sqlSession = sqlSession;
	}
	
	public List<Note> findByAccount(int accountId) {
		this.accountService.checkAccess(accountId);
		return this.sqlSession.selectList("selectNotesByAccount", accountId);
	}
	
	public List<Note> findByContact(int contactId) {
		this.contactService.checkAccess(contactId);
		return this.sqlSession.selectList("selectNotesByContact", contactId);
	}
	
	public List<Note> findByReferral(int referralId) {
		return this.sqlSession.selectList("selectNotesByReferral", referralId);
	}
	
	public Note find(int id) {
		Note ret = this.sqlSession.selectOne("selectNote", id);
		if(ret.getAccountId() > 0) {
			this.accountService.checkAccess(ret.getAccountId());
		}
		if(ret.getContactId() > 0) {
			this.contactService.checkAccess(ret.getContactId());
		}
		return ret;
	}
	
	public void add(Note note) {
		if(note.getAccountId() > 0) {
			this.accountService.checkAccess(note.getAccountId());
		}
		if(note.getContactId() > 0) {
			this.contactService.checkAccess(note.getContactId());
		}
		int result = this.sqlSession.insert("insertNote", note);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
	public void change(Note note) {
		if(note.getAccountId() > 0) {
			this.accountService.checkAccess(note.getAccountId());
		}
		if(note.getContactId() > 0) {
			this.contactService.checkAccess(note.getContactId());
		}
		int result = this.sqlSession.update("updateNote", note);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
	public void delete(int id) {
		Note note = this.find(id);
		if(note.getAccountId() > 0) {
			this.accountService.checkAccess(note.getAccountId());
		}
		if(note.getContactId() > 0) {
			this.contactService.checkAccess(note.getContactId());
		}
		int result = this.sqlSession.delete("deleteNote", id);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void convertReferralNotes(int referralId, int contactId, int accountId) {
		HashMap<String, Object> values = new HashMap<String, Object>();
	    values.put("referralId", referralId);
	    values.put("contactId", contactId);
	    values.put("accountId", accountId);
	    this.sqlSession.update("updateReferralNotes", values);
	}	
	
}