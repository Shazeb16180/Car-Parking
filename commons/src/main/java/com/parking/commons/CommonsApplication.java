package com.parking.commons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.parking.commons.exception.CustomErrorDecoder;

import feign.codec.ErrorDecoder;

@SpringBootApplication
public class CommonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonsApplication.class, args);
	}
	
	@Bean
	public ErrorDecoder getDecoder() {
		return new CustomErrorDecoder();
	}
	
	

}
