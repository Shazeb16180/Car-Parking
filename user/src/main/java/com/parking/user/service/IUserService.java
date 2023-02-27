package com.parking.user.service;

import java.util.List;

import com.parking.commons.model.User;

public interface IUserService {
	public User addUser(User user);

	public List<User> getAllUser();

	public User getUserId(Integer id);
	
	public User getUserName(String userName);
	
}
