package com.parking.availability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.parking")
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.parking.commons.rest")
public class AvailabilityApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvailabilityApplication.class, args);
	}
}