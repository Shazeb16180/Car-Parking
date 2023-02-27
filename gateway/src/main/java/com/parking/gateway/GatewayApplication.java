package com.parking.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.parking.gateway.filter.ZuulPreFilter;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages = "com.parking")
@EnableEurekaClient
@EnableZuulProxy
@EnableFeignClients(basePackages = "com.parking.commons.rest")
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public ZuulPreFilter getPre() {
		return new ZuulPreFilter();
	}

}
