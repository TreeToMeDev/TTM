package com.ttm.crm.server.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.dao.HistoryDao;
import com.ttm.crm.server.dao.NotesDao;
import com.ttm.crm.server.entity.History;
import com.ttm.crm.server.entity.Note;
import com.ttm.crm.server.history.Differ;
import com.ttm.crm.server.history.HistoryAction;
import com.ttm.crm.server.security.IAuthenticationFacade;

@Service
public class NotesService {

	private Differ differ;
	private HistoryDao historyDao;
	private NotesDao notesDao;
	private SessionService sessionService;

	@Autowired
	public NotesService(Differ differ, HistoryDao historyDao, NotesDao noteDao, SessionService sessionService) {
		this.differ = differ;
		this.historyDao = historyDao;
		this.notesDao = noteDao;
		this.sessionService = sessionService;
	}
	
	@Autowired
    private IAuthenticationFacade authenticationFacade;
	
	public void add(Note note) {
		note.setUserId(sessionService.getSession().getUserId());
		if(note.getText() == null) {
			note.setText("");
		}
		note.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		notesDao.add(note);
		historyDao.add(new History(HistoryAction.ADD, note));
	}

	public Note find(int id) {
		return notesDao.find(id);
	}

	public List<Note> findByAccount(int accountId) {
		return notesDao.findByAccount(accountId);
	}

	public List<Note> findByContact(int contactId) {
		return notesDao.findByContact(contactId);
	}
	
	public List<Note> findByReferral(int referralId) {
		return notesDao.findByReferral(referralId);
	}
	
	public void delete(int id) {
		Note oldNote = find(id);
		historyDao.add(new History(HistoryAction.DELETE, oldNote));
		notesDao.delete(id);
	}
	
	public void change(Note newNote) {
		Note oldNote = find(newNote.getId());
		newNote.setUserId(sessionService.getSession().getUserId());
		if(newNote.getText() == null) {
			newNote.setText("");
		}
		newNote.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		notesDao.change(newNote);
		differ.logDiffs(oldNote, newNote); 
	}

}
