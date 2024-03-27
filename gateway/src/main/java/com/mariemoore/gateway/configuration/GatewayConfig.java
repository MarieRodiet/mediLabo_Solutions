package com.mariemoore.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/patients/**")
                        .uri("http://patientsm:9001"))  // Use service name 'patientsm'
                .route(r -> r.path("/api/notes/**")
                        .uri("http://notes-ms:9000"))  // Use service name 'notes-ms'
                .route(r -> r.path("/api/healthrisks/**")
                        .uri("http://healthrisks:9003"))  // Use service name 'healthrisk'
                .build();
    }
}
