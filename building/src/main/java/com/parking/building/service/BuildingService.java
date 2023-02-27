package com.parking.building.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.parking.building.repository.BuildingRepository;
import com.parking.commons.exception.ResourceNotFoundException;
import com.parking.commons.model.Address;
import com.parking.commons.model.Availability;
import com.parking.commons.model.Building;
import com.parking.commons.model.Floor;
import com.parking.commons.model.Slot;
import com.parking.commons.model.User;
import com.parking.commons.rest.UserFeignClient;

@Service
public class BuildingService implements IBuildingService {
	@Autowired
	private UserFeignClient userFeignClient;
	@Autowired
	private BuildingRepository buildingRepository;

	public Integer getId() {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();
		return (Integer) token.getPrincipal();
	}

	public String getRoleHeader() {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();
		Collection<GrantedAuthority> role = token.getAuthorities();
		for (GrantedAuthority r : role) {
			if (r.getAuthority().equals("ADMIN"))
				return "ADMIN";
		}
		return "USER";
	}

	@Override
	public Building addBuilding(Building building) {
		building.setUser(userFeignClient.getUserById(getId()).getBody());
		return buildingRepository.save(building);
	}

	@Override
	public List<Building> getBuildings() {
		if (getRoleHeader().equals("USER")) {
			List<Building> buildings = buildingRepository.findAll();
			if (buildings.isEmpty())
				throw new ResourceNotFoundException("No Buildings");
			else
				return buildings;
		} else {
			User user = userFeignClient.getUserById(getId()).getBody();
			if (user.getBuildings().isEmpty())
				throw new ResourceNotFoundException("No Buildings");
			else
				return user.getBuildings();
		}
	}

	@Override
	public Building getBuilding(Integer buildingId) {
		List<Building> buildings = getBuildings();
		buildings = buildings.stream().filter(building -> building.getId().equals(buildingId))
				.collect(Collectors.toList());
		if (buildings.isEmpty())
			throw new ResourceNotFoundException("No Building With Id:" + buildingId);
		else
			return buildings.stream().findFirst().get();

	}

	@Override
	public List<Building> searchBuilding(String name, String pinCode) {
		if (name == null && pinCode == null)
			return getBuildings();
		else {
			List<Building> resultBuilding = new ArrayList<Building>();
			if (name != null && pinCode != null) {
				resultBuilding.addAll(getBuildings(name));
				resultBuilding.addAll(getBildingsHavingPincode(pinCode));
				if (resultBuilding.size() > 0)
					return resultBuilding.stream().distinct().collect(Collectors.toList());
				else
					throw new ResourceNotFoundException("No Buildings Associated with Name or Pin Code");
			} else if (name != null) {
				resultBuilding.addAll(getBuildings(name));
				if (resultBuilding.size() > 0)
					return resultBuilding;
				else
					throw new ResourceNotFoundException("No Buildings Associated with Name");
			} else {
				resultBuilding.addAll(getBildingsHavingPincode(pinCode));
				if (resultBuilding.size() > 0)
					return resultBuilding;
				else
					throw new ResourceNotFoundException("No Buildings Associated with Pin Code");
			}
		}
	}

	public List<Building> getBuildings(String name) {
		List<Building> building = getBuildings();
		building = building.stream().filter(build -> build.getBuildingName().equalsIgnoreCase(name))
				.collect(Collectors.toList());
		if (building.size() > 0)
			return building;
		else
			return new ArrayList<Building>();
	}

	public List<Building> getBildingsHavingPincode(String pinCode) {
		List<Address> address = getBuildings().stream().filter(building -> building.getAddress() != null)
				.map(building -> building.getAddress()).collect(Collectors.toList());
		address = address.stream().filter(addr -> addr.getPincode().equalsIgnoreCase(pinCode))
				.collect(Collectors.toList());
		if (address.size() > 0)
			return (address.stream().map(addr -> addr.getBuilding()).collect(Collectors.toList()));
		else
			return new ArrayList<Building>();
	}

	@Override
	public Building updateBuilding(Building building, Integer buildingId) {
		Building buildingOriginal = getBuilding(buildingId);
		if (building.getBuildingName() != null)
			buildingOriginal.setBuildingName(building.getBuildingName());
		if (building.getAddress() != null) {
			Address addressOriginal = buildingOriginal.getAddress();
			Address address = building.getAddress();
			if (address.getArea() != null)
				addressOriginal.setArea(address.getArea());
			if (address.getLandmark() != null)
				addressOriginal.setLandmark(address.getLandmark());
			if (address.getState() != null)
				addressOriginal.setState(address.getState());
			if (address.getTown() != null)
				addressOriginal.setTown(address.getTown());
			if (address.getPincode() != null)
				addressOriginal.setPincode(address.getPincode());
		}
		return buildingRepository.save(buildingOriginal);

	}

	public List<Floor> getBuildingAvailability(Integer id) {
		Building building = getBuilding(id);
		List<Floor> floors = building.getFloor();
		if (floors.isEmpty())
			throw new ResourceNotFoundException("Building has no Floors So no Slots,No Availability");
		floors = floors.stream().filter(floor -> floor.getSlot().size() > 0).collect(Collectors.toList());
		if (floors.isEmpty())
			throw new ResourceNotFoundException("Building Has No slots So No availabilty");
		List<Floor> resultFloor = new ArrayList<Floor>();
		for (Floor floor : floors) {
			for (Slot slot : floor.getSlot()) {
				if (slot.getAvailabilty() != null || slot.getAvailabilty().size() > 0) {
					List<Availability> newAvailability = slot.getAvailabilty().stream()
							.filter(avail -> avail.getStatus().equalsIgnoreCase("AVAILABLE"))
							.collect(Collectors.toList());
					if (newAvailability.size() > 0) {
						List<Slot> newSlots = newAvailability.stream().map(avail -> avail.getSlot())
								.collect(Collectors.toList());
						Floor newFloor = new Floor();
						newFloor.setFloorNumber(floor.getFloorNumber());
						newFloor.setSlot(newSlots);
						resultFloor.add(newFloor);
					}
				}
			}

		}
		if (resultFloor.isEmpty())
			throw new ResourceNotFoundException("No Availability");
		else
			return resultFloor;

	}

	@Override
	public String deleteBuilding(Integer buildingId) {
		Building build = getBuilding(buildingId);
		build.setUser(null);
		buildingRepository.delete(build);
		return "Building with Id:" + buildingId + " Deleted";
	}
}
