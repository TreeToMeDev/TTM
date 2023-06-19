package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.FileUpload;

@Component
public class FileUploadDao {
	
	private final SqlSession sqlSession;

	public FileUploadDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<FileUpload> find() {
		return this.sqlSession.selectList("selectFileUploads");
	}
	
	public FileUpload find(int id) {
		return this.sqlSession.selectOne("selectFileUpload", id);
	}
	
	public FileUpload findByFileName(String fileName) {
		return this.sqlSession.selectOne("selectFileUploadByFileName", fileName);
	}
	
	public FileUpload findByFileCode(String fileCode) {
		return this.sqlSession.selectOne("selectFileUploadByFileCode", fileCode);
	}
	
	public void add(FileUpload fileUpload) {
		int result = this.sqlSession.insert("insertFileUpload", fileUpload);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void update(FileUpload fileUpload) {
		int result = this.sqlSession.update("updateFileUpload", fileUpload);
		if(result != 1) {
			throw new RuntimeException();
		}
	}
	
}
