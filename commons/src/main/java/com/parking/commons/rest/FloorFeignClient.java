package com.parking.commons.rest;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.parking.commons.exception.CustomErrorDecoder;
import com.parking.commons.model.Floor;

@FeignClient(value = "FloorApp",configuration =CustomErrorDecoder.class)
public interface FloorFeignClient {
	@GetMapping("/building/{id}/floor/{number}")
	public ResponseEntity<Floor> getFloor(@PathVariable Integer id,@PathVariable Integer number);
	@GetMapping("/building/{id}/floor")
	public ResponseEntity<List<Floor>> getFloors(@PathVariable Integer id);

}
