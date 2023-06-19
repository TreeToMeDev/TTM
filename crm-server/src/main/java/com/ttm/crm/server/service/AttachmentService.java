package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.dao.AttachmentDao;
import com.ttm.crm.server.dao.HistoryDao;
import com.ttm.crm.server.entity.Attachment;
import com.ttm.crm.server.entity.History;
import com.ttm.crm.server.filestorage.googledrive.service.GoogleDriveService;
import com.ttm.crm.server.history.HistoryAction;
import com.ttm.crm.server.security.IAuthenticationFacade;

@Service
public class AttachmentService {
	private AttachmentDao attachmentDao;
	private HistoryDao historyDao;
	private SessionService sessionService;
	
	@Autowired
	public AttachmentService(AttachmentDao attachmentDao,
						     SessionService sessionService, 
						     HistoryDao historyDao) {
		this.attachmentDao = attachmentDao;
		this.sessionService = sessionService;
		this.historyDao = historyDao;
	}
	
	@Autowired
    private IAuthenticationFacade authenticationFacade;
	
	public void add(Attachment attachment) {	
		attachment.setUserId(sessionService.getSession().getUserId());
		attachmentDao.add(attachment);
		historyDao.add(new History(HistoryAction.ADD, attachment));
	}

	public Attachment find(int id) {
		return attachmentDao.find(id);
	}

	public List<Attachment> findByAccount(int accountId) {
		return attachmentDao.findByAccount(accountId);
	}

	public void delete(int id) {
		Attachment oldAttachment = find(id);
		historyDao.add(new History(HistoryAction.DELETE, oldAttachment));
		attachmentDao.delete(id);
	}
}
