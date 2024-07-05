package com.clientui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * The entry point of the Client UI Spring Boot application.
 * This application is configured to use Feign clients for inter-service communication
 * and explicitly disables DataSource auto-configuration as it does not directly manage a database.
 */
@EnableFeignClients("com.clientui")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ClientUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientUiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
