package com.parking.floor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.parking")
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.parking.commons.rest")
public class FloorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FloorApplication.class, args);
	}
	

}
