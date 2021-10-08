package com.springboot.app.gateway.springbootgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringbootGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootGatewayServiceApplication.class, args);
	}

}
