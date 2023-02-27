package com.parking.building.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.commons.model.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
	//public Optional<Building> findByBuildingName(String name);
	public List<Building> findByBuildingName(String name);

}
