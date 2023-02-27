package com.parking.security.microservice;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.parking.commons.model.User;
import com.parking.commons.rest.UserFeignClient;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserFeignClient userFeignClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userFeignClient.getUserByName(username).getBody();
		if (user == null)
			throw new UsernameNotFoundException("Invalid username or password.");

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getRole().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
	}

}
