package com.ttm.crm.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Agent;
import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.service.AddAgentResponse;
import com.ttm.crm.server.service.AgentService;

@RestController
@RequestMapping("/agents")
public class AgentController {

	private AgentService agentService;
	
	public AgentController(AgentService agentService) {
		this.agentService = agentService;
	}
	
	@GetMapping
    public List<Agent> get() {
		return agentService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Agent> get(@PathVariable("id") int id) {
		Agent agent = agentService.find(id);
		return ResponseEntity.ok().body(agent);
	}
	
	@PostMapping
	public ResponseEntity<String> post(@Valid @RequestBody Agent agent) {
		// would prefer to return AddAgentResponse but Spring is choking on this,
		// with String it works fine
		AddAgentResponse resp = agentService.add(agent);
		// this sucks but hack for temporary unsolveable Spring error
		String ret = "{ \"password\": \"" + resp.password + "\"}";
		return ResponseEntity.ok().body(ret);
	}
	
	@PatchMapping("/{id}")
    public ResponseEntity<Contact> patch(@PathVariable("id") int id, @RequestBody Agent agent) {
		agent.setId(id);
		agentService.update(agent);
		return null;
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
		agentService.delete(id);
		return ResponseEntity.ok().body(true);
	}
	
}
