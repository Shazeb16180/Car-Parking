package com.parking.commons.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.parking.commons.exception.CustomErrorDecoder;
import com.parking.commons.interceptor.FeignInterceptor;
import com.parking.commons.model.User;

@FeignClient(value = "UserApp", configuration = {CustomErrorDecoder.class,FeignInterceptor.class})
public interface UserFeignClient {

	@PostMapping("/user")
	public ResponseEntity<User> saveUser(@RequestBody User user);

	@GetMapping("/users/{username}")
	public ResponseEntity<User> getUserByName(@PathVariable String username);
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Integer id);

}
