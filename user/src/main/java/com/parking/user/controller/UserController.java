package com.parking.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.parking.commons.model.User;
import com.parking.user.service.IUserService;

@RestController
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("/user")
	public ResponseEntity<User> addUser(@Validated @RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
	}

	@GetMapping("/user")
	public ResponseEntity<List<User>> getAllUser() {
		return ResponseEntity.ok(userService.getAllUser());
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable Integer id) {
		return ResponseEntity.ok(userService.getUserId(id));

	}

	@GetMapping("/users/{name}")
	public ResponseEntity<User> getUserByName(@PathVariable String name) {
		return ResponseEntity.ok(userService.getUserName(name));

	}

}
