package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.Denullifier;
import com.ttm.crm.server.dao.UserDao;
import com.ttm.crm.server.entity.User;
import com.ttm.crm.server.security.Auth0;
import com.ttm.crm.server.security.RandomStringGenerator;

@Service
public class UserService {

	private UserDao userDao;

	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public AddUserResponse add(User user) {
		Denullifier.denullify(user);
		// add the user to Auth0
		AddUserResponse ret = new AddUserResponse();
		ret.password = RandomStringGenerator.getTricky(3);
		try {
			user.setAuth0Id(Auth0.addUser(user.getEmail(), ret.password));
		} catch(Exception e) {
			System.out.println("error adding user: " + e + " " + e.getMessage());
		}
		if(user.getAuth0Id() != null && user.getAuth0Id().length() > 1) {
			userDao.add(user);
		} else {
			ret.password = "";
			ret.message = "There was an error adding this user. Are you sure they don't already have an account?";
		}
		return ret;
	}

	public void update(User user) {
		Denullifier.denullify(user);
		userDao.update(user);
	}
	
	public List<User> findAll() {
		return userDao.findAll();
	}

	public User find(int id) {
		return userDao.find(id);
	}
	
	public void delete(int id) {
		userDao.delete(id);
	}
	
}
