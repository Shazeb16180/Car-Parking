package com.parking.slot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parking.commons.model.Slot;

public interface SlotRepository extends JpaRepository<Slot, Integer> {
	public Slot findBySlotId(String id);

	@Query(value = "select * from slot where slot_id=?2 and fk_floor=?1", nativeQuery = true)
	public Optional<Slot> findByFloorIdAndSlotId(Integer floorId, String slotId);

}
