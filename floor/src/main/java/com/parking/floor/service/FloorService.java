package com.parking.floor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.commons.exception.ResourceNotFoundException;
import com.parking.commons.model.Floor;
import com.parking.commons.rest.BuildingFeignClient;
import com.parking.floor.repository.FloorRepository;

@Service
public class FloorService implements IFloorService {
	@Autowired
	private BuildingFeignClient buildingfeignClient;

	@Autowired
	private FloorRepository floorRepository;

	@Override
	public Floor addFloor(Integer buildingId, Floor floor) {
		if (buildingfeignClient.getBuilding(buildingId).hasBody()) {
			System.out.println("FloorService.addFloor()");
			floor.setBuilding(buildingfeignClient.getBuilding(buildingId).getBody());
			return floorRepository.save(floor);
		} else
			throw new ResourceNotFoundException("No Building With Id:" + buildingId);
	}

	@Override
	public List<Floor> getFloors(Integer buildingId) {
		if (buildingfeignClient.getBuilding(buildingId).getBody().getFloor().isEmpty())
			throw new ResourceNotFoundException("No Floors");
		else
			return buildingfeignClient.getBuilding(buildingId).getBody().getFloor();
	}

	@Override
	public Floor getFloor(Integer buildingId, Integer floorNumber) {
		List<Floor> floor = getFloors(buildingId);
		floor = floor.stream().filter(flr -> flr.getFloorNumber().equals(floorNumber)).collect(Collectors.toList());
		// Building building=buildingfeignClient.getBuilding(buildingId).getBody();//
		// This will check is building id valid or not
		if (floor.isEmpty())
			throw new ResourceNotFoundException("No Floor With Number:" + floorNumber);
		else
			return floor.stream().findFirst().get();
	}

	@Override
	public String deleteFloor(Integer buildingId, Integer floorNumber) {
		Floor floor = getFloor(buildingId, floorNumber);
		floor.setBuilding(null);
		floorRepository.delete(floor);
		return "Floor " + floorNumber + " Deleted";
	}

}