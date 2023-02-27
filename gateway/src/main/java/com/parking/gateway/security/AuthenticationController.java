package com.parking.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.parking.commons.model.LoginAdminResponse;
import com.parking.commons.model.LoginRequest;
import com.parking.commons.model.LoginUserResponse;
import com.parking.commons.model.User;
import com.parking.commons.rest.UserFeignClient;
import com.parking.security.microservice.JwtUtil;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserFeignClient userFeignClient;

	@PostMapping("/signup")
	public ResponseEntity<String> doSignUp(@RequestBody User User) {
		System.out.println("AuthenticationController.doSignUp()");
		User user = userFeignClient.saveUser(User).getBody();
		System.out.println("user=" + user);
		return ResponseEntity.ok("User Created");

	}

	@PostMapping("/login")
	public ResponseEntity<?> doLogin(@RequestBody LoginRequest loginRequest) {
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtUtil.generateToken(userFeignClient.getUserByName(loginRequest.getUsername()).getBody());
		User user = userFeignClient.getUserById(jwtUtil.getId(jwt)).getBody();
		if (jwtUtil.getRole(jwt).equalsIgnoreCase("ADMIN")) {
			return ResponseEntity.ok(new LoginAdminResponse(jwt, user.getBuildings()));
		} else {
			return ResponseEntity.ok(new LoginUserResponse(jwt, user.getAvailability()));
		}

	}

}
