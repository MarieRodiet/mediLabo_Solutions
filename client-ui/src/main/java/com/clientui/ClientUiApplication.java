package com.clientui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*
* L'annotation   @EnableFeignClients  demande à Feign de scanner le package "com.clientui"
*   pour rechercher des classes qui se déclarent clients Feign. */
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
