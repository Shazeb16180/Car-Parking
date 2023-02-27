package com.parking.slot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.parking.commons.model.Slot;
import com.parking.slot.service.ISlotService;

@RestController
public class SlotController {

	@Autowired
	private ISlotService slotService;

	@PostMapping("/building/{buildingId}/floor/{floorNumber}/slot")
	public ResponseEntity<Slot> addSlot(@Validated @RequestBody Slot slot, @PathVariable Integer buildingId,
			@PathVariable Integer floorNumber) {
		return ResponseEntity.status(HttpStatus.CREATED).body(slotService.addSlot(slot, buildingId, floorNumber));
	}

	@GetMapping("/building/{buildingId}/floor/{floorNumber}/slot")
	public ResponseEntity<List<Slot>> getSlots(@PathVariable Integer buildingId, @PathVariable Integer floorNumber) {
		return ResponseEntity.ok(slotService.getSlots(buildingId, floorNumber));
	}

	@GetMapping("/building/{buildingId}/floor/{floorNumber}/slot/{slotNumber}")
	public ResponseEntity<Slot> getSlot(@PathVariable Integer buildingId, @PathVariable Integer floorNumber,
			@PathVariable String slotNumber) {
		return ResponseEntity.ok(slotService.getSlot(buildingId, floorNumber, slotNumber));
	}

	@DeleteMapping("/building/{buildingId}/floor/{floorNumber}/slot/{slotNumber}")
	public ResponseEntity<String> deleteSlot(@PathVariable Integer buildingId, @PathVariable Integer floorNumber,
			@PathVariable String slotNumber) {
		return ResponseEntity.ok(slotService.deleteSlot(buildingId, floorNumber, slotNumber));
	}

}
