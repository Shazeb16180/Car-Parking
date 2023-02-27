package com.parking.availability.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.parking.availability.repository.AvailabilityRepository;
import com.parking.commons.exception.ResourceNotFoundException;
import com.parking.commons.model.Availability;
import com.parking.commons.model.Slot;
import com.parking.commons.rest.SlotFeignClient;
import com.parking.commons.rest.UserFeignClient;

@Service
public class AvailabilityService implements IAvailabilityService {
	@Autowired
	private SlotFeignClient slotFeignClient;

	@Autowired
	private UserFeignClient userFeignClient;

	@Autowired
	private AvailabilityRepository availabilityRepository;
	
	public Integer getId() {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();
		return (Integer) token.getPrincipal();
	}

	@Override
	public Availability addAvailability(@RequestBody Availability availability, Integer buildingNumber,
			Integer floorNumber, String slotNumber) {
		Slot slot = slotFeignClient.getSlot(buildingNumber, floorNumber, slotNumber).getBody();
		availability.setSlot(slot);
		List<Availability> avail = availabilityRepository.getAvailability(slot.getId());
		System.out.println(avail);
		if (avail == null || avail.size() <= 0)
			return availabilityRepository.save(availability);
		else {
			List<Availability> alreadyAvailable = avail.stream()
					.filter(avail1 -> avail1.getDate().equals(availability.getDate())
							&& (avail1.getStatus().equals("AVAILABLE") || avail1.getStatus().equals("BOOKED")))
					.collect(Collectors.toList());
			System.out.println(alreadyAvailable);
			if (alreadyAvailable.isEmpty()) {
				List<Availability> cancelAvails = avail.stream()
						.filter(av -> (av.getDate().equals(availability.getDate()) && av.getStatus().equals("CANCEL")))
						.collect(Collectors.toList());
				if (cancelAvails.isEmpty())
					return availabilityRepository.save(availability);
				else {
					Availability cancelAvail = cancelAvails.stream().findFirst().get();
					cancelAvail.setStatus("AVAILABLE");
					return availabilityRepository.save(cancelAvail);
				}
			}

			else
				throw new ResourceNotFoundException("Already Avaialble or Booked ");
		}
	}

	@Override
	public List<Availability> getAvailabilities(Integer buildingNumber, Integer floorNumber, String slotNumber) {
		Slot slot = slotFeignClient.getSlot(buildingNumber, floorNumber, slotNumber).getBody();
		if (slot.getAvailabilty().isEmpty())
			throw new ResourceNotFoundException("No Availability For the Slot:" + slotNumber);
		else
			return slot.getAvailabilty();
	}

	@Override
	public Availability getAvailability(Integer buildingNumber, Integer floorNumber, String slotNumber,
			Integer availabilityId) {
		// Slot slot = slotFeignClient.getSlot(buildingNumber, floorNumber,
		// slotNumber).getBody();
		List<Availability> availabilities = getAvailabilities(buildingNumber, floorNumber, slotNumber);
		availabilities = availabilities.stream().filter(avail -> avail.getId().equals(availabilityId))
				.collect(Collectors.toList());
		// Optional<Availability> availability =
		// availabilityRepository.findByAvailabilityIdAndSlotId(availabilityId,
		// slot.getId());
		if (availabilities.isEmpty())
			throw new ResourceNotFoundException("No Availability with Id:" + availabilityId);
		else
			return availabilities.stream().findFirst().get();

	}

	@Override
	public String deleteAvailability(Integer buildingNumber, Integer floorNumber, String slotNumber,
			Integer availabilityId) {
		Availability availability = getAvailability(buildingNumber, floorNumber, slotNumber, availabilityId);
		if (availability.getStatus().equals("BOOKED"))
			throw new ResourceNotFoundException(
					"Available Status:" + availability.getStatus() + " Can't Delete Availability");
		else {
			availability.setSlot(null);
			availabilityRepository.delete(availability);
			return "Availability Cancelled Having Id:" + availabilityId;
		}
	}

	@Override
	public String bookSlot(Integer buildingId, Integer floorNumber, String slotNumber, LocalDate date) {
		Slot slot = slotFeignClient.getSlot(buildingId, floorNumber, slotNumber).getBody();
		List<Availability> availability = slot.getAvailabilty();
		if (availability.size() > 0) {
			List<Availability> bookAvail = availability.stream()
					.filter(avail -> avail.getDate().equals(date)
							&& (avail.getStatus().equals("AVAILABLE") || avail.getStatus().equals("CANCELED")))
					.collect(Collectors.toList());
			// bookAvail.size()>0?bookAvail.stream().findFirst().get().setStatus("Booked"):;
			if (bookAvail.size() > 0) {
				Availability av = bookAvail.stream().findFirst().get();
				av.setStatus("Booked");
				av.setSlot(slot);
				av.setUser(userFeignClient.getUserById(getId()).getBody());
				availabilityRepository.save(av);
			} else {
				throw new ResourceNotFoundException("Already Booked on That Day Or No Availability Added");
			}

		} else
			throw new ResourceNotFoundException("No Availability Added for the Slot");

		return null;
	}

	@Override
	public String cancelSlot(Integer buildingId, Integer floorNumber, String slotNumber, LocalDate date) {
		Slot slot = slotFeignClient.getSlot(buildingId, floorNumber, slotNumber).getBody();
		List<Availability> availability = slot.getAvailabilty();
		if (availability.size() > 0) {
			List<Availability> bookedAvail = availability.stream()
					.filter(avail -> avail.getDate().equals(date) && avail.getStatus().equals("Booked"))
					.collect(Collectors.toList());
			if (bookedAvail.size() > 0) {
				Availability cancelAvail = bookedAvail.stream().findFirst().get();
				cancelAvail.setStatus("CANCELED");
				cancelAvail.setSlot(slot);
				cancelAvail.setUser(null);
				availabilityRepository.save(cancelAvail);
			} else {
				throw new ResourceNotFoundException(
						"Either Booking is Not Available on date :" + date + " Or Cancelled");
			}
		} else
			throw new ResourceNotFoundException("No Availability for Slots");

		return null;
	}

}
