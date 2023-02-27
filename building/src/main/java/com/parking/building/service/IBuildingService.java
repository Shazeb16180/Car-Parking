package com.parking.building.service;

import java.util.List;

import com.parking.commons.model.Building;
import com.parking.commons.model.Floor;

public interface IBuildingService {
	public Integer getId();

	public String getRoleHeader();

	public Building addBuilding(Building building);

	public List<Building> getBuildings();

	public Building getBuilding(Integer buildingId);

	public List<Building> searchBuilding(String name, String pinCode);

	public Building updateBuilding(Building building, Integer buildingId);

	public List<Floor> getBuildingAvailability(Integer id);

	public String deleteBuilding(Integer buildingId);
}
