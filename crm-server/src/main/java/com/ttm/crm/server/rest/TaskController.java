package com.ttm.crm.server.rest;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ttm.crm.server.entity.Task;
import com.ttm.crm.server.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@GetMapping
    public List<Task> get(@RequestParam("filter") String taskFilter) {
		switch (taskFilter) {
		case "all":
			return taskService.findAll();
		case "open":
			return taskService.findAllOpen();	
		}
		
		return new ArrayList<Task>();
	}

	@GetMapping("/account/{accountId}")
    public List<Task> getTasksByAccountId(@PathVariable("accountId") int accountId, @RequestParam("filter") String taskFilter) {
		
		switch (taskFilter) {
		case "all":
			return taskService.findAllByAccountId(accountId);
		case "open":
			return taskService.findAllOpenByAccountId(accountId);	
		}
		
		return new ArrayList<Task>();
	}
	
	@GetMapping("/contact/{contactId}")
    public List<Task> getTasksByContactId(@PathVariable("contactId") int contactId, @RequestParam("filter") String taskFilter) {
		
		switch (taskFilter) {
		case "all":
			return taskService.findAllByContactId(contactId);
		case "open":
			return taskService.findAllOpenByContactId(contactId);	
		}
		
		return new ArrayList<Task>();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Task> get(@PathVariable("id") int id) {
		Task task = taskService.find(id);
		return ResponseEntity.ok().body(task);
	}
	
	@PostMapping
	public void post(@Valid @RequestBody Task task) {
		taskService.add(task);
	}
	
	@PatchMapping("/{id}")
    public ResponseEntity<Task> patch(@PathVariable("id") int id, @RequestBody Task task) {
		task.setId(id);
		taskService.update(task);
		return null;
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
		taskService.delete(id);
		return ResponseEntity.ok().body(true);
	}
	
}
