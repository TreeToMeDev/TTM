package com.ttm.crm.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttm.crm.server.Denullifier;
import com.ttm.crm.server.dao.AgentDao;
import com.ttm.crm.server.entity.Agent;
import com.ttm.crm.server.security.Auth0;
import com.ttm.crm.server.security.RandomStringGenerator;

@Service
public class AgentService {

	private AgentDao agentDao;

	@Autowired
	public AgentService(AgentDao agentDao) {
		this.agentDao = agentDao;
	}
	
	public AddAgentResponse add(Agent agent) {
		Denullifier.denullify(agent);
		
		// add the agent to Auth0
		// TODO, return the generated password to the UI
		AddAgentResponse ret = new AddAgentResponse();
		ret.password = RandomStringGenerator.getTricky(3);
		try {
			agent.setAuth0Id(Auth0.addUser(agent.getEmail(), ret.password));
		} catch(Exception e) {
			System.out.println("error adding agent: " + e + " " + e.getMessage());
		}
		if(agent.getAuth0Id() != null && agent.getAuth0Id().length() > 1) {
			agentDao.add(agent);
		} else {
			ret.password = "";
			ret.message = "There was an error adding this agent. Are you sure they don't already have an account?";
		}
		return ret;
	}

	public void update(Agent agent) {
		Denullifier.denullify(agent);
		agentDao.update(agent);
	}
	
	public Agent find(int id) {
		return agentDao.find(id);
	}

	public List<Agent> findAll() {
		List<Agent> ret = agentDao.findAll();
		assignLevels(ret);
		return ret;
	}
	
	private void assignLevels(List<Agent> agents) {
		for(Agent agent : agents) {
			agent.setLevel(getLevel(agent, agents, 0));
		}
	}
	
	private int getLevel(Agent agent, List<Agent> agents, int level) {
		if(agent.getParentId() == 0) {
			return level;
		} else {
			for(Agent agent2 : agents) {
				// prevent stack overflow in case parent_id is accidentally set to point
				// back to the same user
				if(agent2.getId() == agent2.getParentId()) {
					System.out.println("cannot walk tree for agent: " + agent.getId());
					return -2;
				}
				if(agent2.getId() == agent.getParentId()) {
					return getLevel(agent2, agents, ++level);
				}
			}
			System.out.println("cannot walk tree for agent: " + agent.getId());
			return -2;
		}
	}

	
	public void delete(int id) {
		agentDao.delete(id);
	}
	
}
