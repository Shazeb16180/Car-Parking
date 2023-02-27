package com.parking.building.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parking.commons.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
