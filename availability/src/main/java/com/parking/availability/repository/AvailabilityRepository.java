package com.parking.availability.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parking.commons.model.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
	@Query(value = "select * from availability where slot_fk=?1", nativeQuery = true)
	public List<Availability> getAvailability(Integer slotId);

	@Query(value = "select * from availability where id=?1 and slot_fk=?2", nativeQuery = true)
	public Optional<Availability> findByAvailabilityIdAndSlotId(Integer availabilityId, Integer slotId);
}
