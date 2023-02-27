package com.parking.building.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parking.building.service.IBuildingService;
import com.parking.commons.model.Building;
import com.parking.commons.model.Floor;

@RestController
public class BuildingController {
	@Autowired
	private IBuildingService buildingService;

	@PostMapping("/building")
	public ResponseEntity<Building> addBuilding(@Validated @RequestBody Building building) {
		return ResponseEntity.status(HttpStatus.CREATED).body(buildingService.addBuilding(building));
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/building")
	public ResponseEntity<List<Building>> getBuildings() {
		return ResponseEntity.ok(buildingService.getBuildings());
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/building/{id}")
	public ResponseEntity<Building> getBuilding(@PathVariable Integer id) {
		return ResponseEntity.ok(buildingService.getBuilding(id));
	}

	@GetMapping("/building/search")
	public ResponseEntity<List<Building>> getBuildingByNameorPincode(@RequestParam(required = false) String name,
			@RequestParam(required = false) String pincode) {
		return ResponseEntity.ok(buildingService.searchBuilding(name, pincode));
	}

	@GetMapping("/building//{id}/available")
	public ResponseEntity<List<Floor>> getBuildingAvailability(@PathVariable Integer id) {
		return ResponseEntity.ok(buildingService.getBuildingAvailability(id));
	}

	@PutMapping("/building/{id}")
	public ResponseEntity<Building> updateBuilding(@RequestBody Building building, @PathVariable Integer id) {
		return ResponseEntity.ok(buildingService.updateBuilding(building, id));
	}

	@DeleteMapping("/building/{id}")
	public ResponseEntity<String> deleteBuilding(@PathVariable Integer id) {
		return ResponseEntity.ok(buildingService.deleteBuilding(id));
	}

}
