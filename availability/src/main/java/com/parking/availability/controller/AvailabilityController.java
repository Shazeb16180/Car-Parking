package com.parking.availability.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parking.availability.service.IAvailabilityService;
import com.parking.commons.model.Availability;

@RestController
public class AvailabilityController {

	@Autowired
	private IAvailabilityService availabilityService;

	@PostMapping("/building/{buildingId}/floor/{floorNumber}/slot/{slotNumber}/availability")
	public ResponseEntity<Availability> addAvailability(@RequestBody Availability availability,
			@PathVariable Integer buildingId, @PathVariable Integer floorNumber, @PathVariable String slotNumber) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(availabilityService.addAvailability(availability, buildingId, floorNumber, slotNumber));
	}

	@GetMapping("/building/{buildingId}/floor/{floorNumber}/slot/{slotNumber}/availability")
	public ResponseEntity<List<Availability>> getAvailabilities(@PathVariable Integer buildingId,
			@PathVariable Integer floorNumber, @PathVariable String slotNumber) {
		return ResponseEntity.ok(availabilityService.getAvailabilities(buildingId, floorNumber, slotNumber));
	}

	@GetMapping("/building/{buildingId}/floor/{floorNumber}/slot/{slotNumber}/availability/{availabilityId}")
	public ResponseEntity<Availability> getAvailability(@PathVariable Integer buildingId,
			@PathVariable Integer floorNumber, @PathVariable String slotNumber, @PathVariable Integer availabilityId) {
		return ResponseEntity
				.ok(availabilityService.getAvailability(buildingId, floorNumber, slotNumber, availabilityId));
	}

	@DeleteMapping("/building/{buildingId}/floor/{floorNumber}/slot/{slotNumber}/availability/{availabilityId}")
	public ResponseEntity<String> deleteAvailability(@PathVariable Integer buildingId,
			@PathVariable Integer floorNumber, @PathVariable String slotNumber, @PathVariable Integer availabilityId) {
		return ResponseEntity
				.ok(availabilityService.deleteAvailability(buildingId, floorNumber, slotNumber, availabilityId));
	}

	@PostMapping("/building/{buildingId}/floor/{floorNumber}/slot/{slotNumber}/book")
	public ResponseEntity<String> bookSlot(@PathVariable Integer buildingId, @PathVariable Integer floorNumber,
			@PathVariable String slotNumber, @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
		return ResponseEntity.ok(availabilityService.bookSlot(buildingId, floorNumber, slotNumber, date));
	}

	@DeleteMapping("/building/{buildingId}/floor/{floorNumber}/slot/{slotNumber}/cancel")
	public ResponseEntity<String> cancelSlot(@PathVariable Integer buildingId, @PathVariable Integer floorNumber,
			@PathVariable String slotNumber, @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
		return ResponseEntity.ok(availabilityService.cancelSlot(buildingId, floorNumber, slotNumber, date));
	}

}
