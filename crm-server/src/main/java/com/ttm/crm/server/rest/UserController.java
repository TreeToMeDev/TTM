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

import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.entity.User;
import com.ttm.crm.server.service.AddUserResponse;
import com.ttm.crm.server.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
    public List<User> get() {
		return userService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> get(@PathVariable("id") int id) {
		User user = userService.find(id);
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping
	public ResponseEntity<String> post(@Valid @RequestBody User user) {
		// would prefer to return AddUserResponse but Spring is choking on this,
		// with String it works fine
		AddUserResponse ret = userService.add(user);
		String pw = ret.password;
		// this sucks but hack for temporary unsolveable Spring error
		pw = "{ \"password\": \"" + pw + "\"}";
		return ResponseEntity.ok().body(pw);
	}
	
	@PatchMapping("/{id}")
    public ResponseEntity<Contact> patch(@PathVariable("id") int id, @RequestBody User user) {
		user.setId(id);
		userService.update(user);
		return null;
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
		userService.delete(id);
		return ResponseEntity.ok().body(true);
	}
	
}
