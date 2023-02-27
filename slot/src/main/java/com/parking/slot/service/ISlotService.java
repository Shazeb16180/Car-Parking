package com.parking.slot.service;

import java.util.List;

import com.parking.commons.model.Slot;

public interface ISlotService {

	public Slot addSlot(Slot slot, Integer buildingId, Integer floorNumber);

	public String deleteSlot(Integer buildingId, Integer floorNumber, String slotId);

	public List<Slot> getSlots(Integer buildingId, Integer floorNumber);

	public Slot getSlot(Integer buildingId, Integer floorNumber, String slotId);

}
