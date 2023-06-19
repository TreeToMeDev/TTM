package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.Attachment;
import com.ttm.crm.server.service.AccountService;

@Component
public class AttachmentDao {

	private final AccountService accountService;
	private final SqlSession sqlSession;
	
	public AttachmentDao(AccountService accountService, SqlSession sqlSession) {
		this.accountService = accountService;
		this.sqlSession = sqlSession;
	}
		
	public List<Attachment> findByAccount(int accountId) {
		this.accountService.checkAccess(accountId);
		return this.sqlSession.selectList("selectAttachmentsByAccount", accountId);
	}

	public Attachment find(int id) {
		Attachment ret = this.sqlSession.selectOne("selectAttachment", id);
		this.accountService.checkAccess(ret.getAccountId());
		return ret;
	}
	
	public void add(Attachment attachment) {
		this.accountService.checkAccess(attachment.getAccountId());
		int result = this.sqlSession.insert("insertAttachment", attachment);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void delete(int id) {
		Attachment attachment = this.find(id);
		this.accountService.checkAccess(attachment.getAccountId());
		int result = this.sqlSession.delete("deleteAttachment", id);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
}
