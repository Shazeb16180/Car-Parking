package com.parking.commons.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.commons.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	public List<Address> findByPincode(String pincode);

}
