package com.parking.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.parking.commons.exception.ResourceNotFoundException;
import com.parking.commons.model.User;
import com.parking.user.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User addUser(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		if (userRepository.findAll().isEmpty())
			throw new ResourceNotFoundException("No Users");
		else
			return userRepository.findAll();
	}

	@Override
	public User getUserId(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty())
			throw new ResourceNotFoundException("No User with Id");
		else
			return user.get();
	}

	@Override
	public User getUserName(String userName) {
		User user = userRepository.findByUsername(userName);
		return user;
	}

}
