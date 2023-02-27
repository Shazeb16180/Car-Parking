package com.parking.slot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.commons.exception.ResourceNotFoundException;
import com.parking.commons.model.Floor;
import com.parking.commons.model.Slot;
import com.parking.commons.rest.FloorFeignClient;
import com.parking.slot.repository.SlotRepository;

@Service
public class SlotService implements ISlotService {
	@Autowired
	private FloorFeignClient floorFeignClient;

	@Autowired
	private SlotRepository slotRepository;

	@Override
	public Slot addSlot(Slot slot, Integer buildingId, Integer floorNumber) {
		Floor floor = floorFeignClient.getFloor(buildingId, floorNumber).getBody();
		slot.setFloor(floor);
		return slotRepository.save(slot);
	}

	@Override
	public List<Slot> getSlots(Integer buildingId, Integer floorNumber) {
		Floor floor = floorFeignClient.getFloor(buildingId, floorNumber).getBody();// This will check building id and
																					// Floor Id is valid or not
		if (floor.getSlot().isEmpty())
			throw new ResourceNotFoundException("No Slots");
		else
			return floor.getSlot();

	}

	@Override
	public Slot getSlot(Integer buildingId, Integer floorNumber, String slotNumber) {
		//Floor floor = floorFeignClient.getFloor(buildingId, floorNumber).getBody();// This will check building id and
																					// Floor Id is valid or not
		List<Slot> slots = getSlots(buildingId, floorNumber);
		slots=slots.stream().filter(slot -> slot.getSlotId().equalsIgnoreCase(slotNumber)).collect(Collectors.toList());
		if (slots.isEmpty())
			throw new ResourceNotFoundException("No Slot With Id:" + slotNumber);
		else
			return slots.stream().findFirst().get();

	}

	@Override
	public String deleteSlot(Integer buildingId, Integer floorNumber, String slotNumber) {
		Slot slot = getSlot(buildingId, floorNumber, slotNumber);
		slot.setFloor(null);
		slotRepository.delete(slot);
		return "Slot " + slotNumber + " Deleted";
	}

}