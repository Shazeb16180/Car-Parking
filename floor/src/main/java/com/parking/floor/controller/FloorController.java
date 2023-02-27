package com.parking.floor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.parking.commons.model.Floor;
import com.parking.floor.service.IFloorService;

@RestController
public class FloorController {
	@Autowired
	private IFloorService floorService;

	@PostMapping("/building/{id}/floor")
	public ResponseEntity<Floor> addFloor(@RequestBody Floor floor, @PathVariable Integer id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(floorService.addFloor(id, floor));
	}

	@GetMapping("/building/{id}/floor")
	public ResponseEntity<List<Floor>> getFloors(@PathVariable Integer id) {
		return ResponseEntity.ok(floorService.getFloors(id));
	}

	@GetMapping("/building/{id}/floor/{number}")
	public ResponseEntity<Floor> getFloor(@PathVariable Integer id, @PathVariable Integer number) {
		return ResponseEntity.ok(floorService.getFloor(id, number));
	}

	@DeleteMapping("/building/{id}/floor/{number}")
	public ResponseEntity<String> deleteFloor(@PathVariable Integer id, @PathVariable Integer number) {
		return ResponseEntity.ok(floorService.deleteFloor(id, number));
	}

}
