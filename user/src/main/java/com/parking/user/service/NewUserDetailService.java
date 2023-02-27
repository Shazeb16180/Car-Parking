package com.parking.user.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.parking.commons.model.User;
import com.parking.user.controller.UserController;

@Component
@Primary
public class NewUserDetailService implements UserDetailsService {

	@Autowired
	private UserController userController;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userController.getUserByName(username).getBody();
		if (user == null)
			throw new UsernameNotFoundException("Invalid username or password.");

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getRole().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
	}

}
