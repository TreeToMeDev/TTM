package com.ttm.crm.server.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.ttm.crm.server.entity.User;

@Component
public class UserDao {

	private final SqlSession sqlSession;
	
	public UserDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// TODO: add History and server-side access control
	
	public List<User> findAll() {
		return this.sqlSession.selectList("selectUsers");
	}
	
	public User find(int id) {
		return this.sqlSession.selectOne("selectUser", id);
	}
	
	public User findByAuth0Id(String auth0Id) {
		return this.sqlSession.selectOne("selectUserByAuth0Id", auth0Id);
	}

	public void add(User user) {
		int result = this.sqlSession.insert("insertUser", user);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void update(User user) {
		int result = this.sqlSession.update("updateUser", user);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

	public void delete(int id) {
		int result = this.sqlSession.delete("deleteUser", id);
		if(result != 1) {
			throw new RuntimeException();
		}
	}

}