package com.ttm.crm.server.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Note;
import com.ttm.crm.server.service.NotesService;

@RestController
@RequestMapping("/notes")
public class NotesController {

	private NotesService notesService;
	
	public NotesController(NotesService notesService) {
		this.notesService = notesService;
	}

	@GetMapping("{id}")
    public Note get(@PathVariable("id") int id) {
		return this.notesService.find(id);
	}

	@GetMapping("account/{accountId}")
    public List<Note> getByAccount(@PathVariable("accountId") int accountId) {
		return this.notesService.findByAccount(accountId);
	}
	
	@GetMapping("contact/{contactId}")
	public List<Note> getByContact(@PathVariable("contactId") int contactId) {
		return this.notesService.findByContact(contactId);
	}
	
	@GetMapping("referral/{referralId}")
	public List<Note> getByReferral(@PathVariable("referralId") int referralId) {
		return this.notesService.findByReferral(referralId);
	}
	
	// TODO proper return type w/edits etc.
	// TODO use DAO numb!!
	@PostMapping
    public ResponseEntity<Note> post(@RequestBody Note note) {
		notesService.add(note);
		return null;
	}

//  Disable editing and deleting of notes.  TZ: 11-13-22
//	@PatchMapping
//    public ResponseEntity<Note> patch(@RequestBody Note note) {
//		notesService.change(note);
//		return null;
//	}
//	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
//		notesService.delete(id);
//		return ResponseEntity.ok().body(new Boolean(true));
//	}
	
}