package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.ContactTypeList;

@Component
public class ContactTypeListDao {

	private final SqlSession sqlSession;
	
	public ContactTypeListDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<ContactTypeList> find() {
		return this.sqlSession.selectList("selectAllContactTypeList");
	}

	public ContactTypeList find(String description) {
		return this.sqlSession.selectOne("selectContactTypeListItem", description);
	}

	public int add(ContactTypeList contactTypeListItem) {
		int result = this.sqlSession.insert("insertContactTypeListItem", contactTypeListItem);
		return result;
	}
	
	public boolean delete(String description) {
		this.sqlSession.delete("deleteContactTypeItem", description);
		return true;
	}
}
