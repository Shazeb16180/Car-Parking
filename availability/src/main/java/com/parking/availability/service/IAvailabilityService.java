package com.parking.availability.service;

import java.time.LocalDate;
import java.util.List;

import com.parking.commons.model.Availability;

public interface IAvailabilityService {
	public Availability addAvailability(Availability availability, Integer buildingNumber, Integer floorNumber,
			String slotNumber);

	public List<Availability> getAvailabilities(Integer buildingNumber, Integer floorNumber, String slotNumber);

	public Availability getAvailability(Integer buildingNumber, Integer floorNumber, String slotNumber,
			Integer availabilityId);

	public String deleteAvailability(Integer buildingNumber, Integer floorNumber, String slotNumber,
			Integer availabilityId);

	public String bookSlot(Integer buildingId, Integer floorNumber, String slotNumber,LocalDate date);
	
	public String cancelSlot(Integer buildingId, Integer floorNumber, String slotNumber,LocalDate date);


}
