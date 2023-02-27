package com.parking.slot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.parking")
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.parking.commons.rest")
public class SlotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlotApplication.class, args);
	}

}
