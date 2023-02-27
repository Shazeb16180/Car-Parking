package com.parking.commons.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.parking.commons.exception.CustomErrorDecoder;
import com.parking.commons.model.Slot;

@FeignClient(value =  "SlotApp",configuration =CustomErrorDecoder.class)
public interface SlotFeignClient {
	@GetMapping("/building/{buildingId}/floor/{floorNumber}/slot/{slotId}")
	public ResponseEntity<Slot> getSlot(@PathVariable Integer buildingId, @PathVariable Integer floorNumber,
			@PathVariable String slotId);

}
