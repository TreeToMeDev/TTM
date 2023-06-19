package com.ttm.crm.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.dao.UserDao;
import com.ttm.crm.server.entity.Session;
import com.ttm.crm.server.entity.User;
import com.ttm.crm.server.security.IAuthenticationFacade;

@Service
public class SessionService {

	private UserDao userDao;

	@Autowired
	public SessionService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
    private IAuthenticationFacade authenticationFacade;
	
	public Session getByAuth0Id(String auth0Id) {
		User user = userDao.findByAuth0Id(auth0Id);
		Session ret = new Session();
		ret.setAccessUsers(user.getAccessUsers());
		ret.setUserId(user.getId());
		ret.setName(user.getFirstName() + " " + user.getLastName());
		ret.setParentId(user.getParentId());
		return ret;
	}
	
	public Session getSession() {
		Authentication authentication = authenticationFacade.getAuthentication();
        if(authentication == null) {
        	System.out.println("AUTH IS NULL, WTF?");
        }
		return getByAuth0Id(authentication.getName());
	}

}
