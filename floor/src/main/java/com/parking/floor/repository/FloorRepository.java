package com.parking.floor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parking.commons.model.Floor;

public interface FloorRepository extends JpaRepository<Floor, Integer> {

	public Optional<Floor> findByFloorNumber(Integer number);

	@Query(value = "select * from floor where building_fk=?1 and floor_number=?2", nativeQuery = true)
	public Optional<Floor> findByBuildingAndFloorNumber(Integer buildingNumber, Integer floorNumber);

}
