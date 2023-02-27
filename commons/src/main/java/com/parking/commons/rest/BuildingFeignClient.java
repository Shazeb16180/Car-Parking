package com.parking.commons.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.parking.commons.exception.CustomErrorDecoder;
import com.parking.commons.model.Building;

@FeignClient(value = "BuildingApp",configuration =CustomErrorDecoder.class)
public interface BuildingFeignClient {
	
	@GetMapping("/building/{id}")
	public ResponseEntity<Building> getBuilding(@PathVariable Integer id);

}
