package com.parking.floor.service;

import java.util.List;

import com.parking.commons.model.Floor;

public interface IFloorService {
	public Floor addFloor(Integer id, Floor floor);

	public List<Floor> getFloors(Integer buildingId);

	public Floor getFloor(Integer buildingId, Integer floorNumber);

	public String deleteFloor(Integer buildingId, Integer floorNumber);

}
