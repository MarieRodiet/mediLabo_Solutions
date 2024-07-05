package com.mariemoore.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Main application class that initializes the gateway service for the system.
 * This gateway serves as the primary entry point for the client UI, facilitating access to various backend microservices
 * including Patients, Notes, and Health Risks. It routes incoming requests to the appropriate microservices based on the request path,
 * abstracting the complexities of the microservices architecture from the client.
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.mariemoore.gateway"})
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
