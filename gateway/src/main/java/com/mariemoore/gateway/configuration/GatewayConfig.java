package com.mariemoore.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the gateway routing rules.
 * This class uses Spring Cloud Gateway to route requests to various microservices
 * based on the URL path patterns. Each route is configured to forward requests
 * to the appropriate microservice.
 */
@Configuration
public class GatewayConfig {

    /**
     * Configures a custom RouteLocator to define specific routing behaviors.
     * This method sets up routes that direct API calls to the appropriate backend services,
     * depending on the API endpoint accessed.
     *
     * @param builder The RouteLocatorBuilder which provides a fluent API to build
     *                the route locator.
     * @return a RouteLocator containing the defined routes.
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/patients/**")
                        .uri("http://patientsm:9001"))  // Use patients microservice
                .route(r -> r.path("/api/notes/**")
                        .uri("http://notes-ms:9000"))  // Use notes microservice
                .route(r -> r.path("/api/healthrisks/**")
                        .uri("http://healthrisks:9003"))  // Use healthrisk  microservice
                .build();
    }
}
