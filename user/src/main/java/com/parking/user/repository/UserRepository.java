package com.parking.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parking.commons.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String userName);

}
