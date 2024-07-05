package com.mariemoore.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configuration class for security settings in a reactive web application.
 * Enables method-level security and sets up JWT-based authentication mechanism.
 */
@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{


    /**
     * Configures a ReactiveAuthenticationManager with a user details service and a password encoder.
     * This manager supports reactive authentication processes, particularly useful for non-blocking applications.
     *
     * @param userDetailsService The reactive user details service for retrieving user information.
     * @param passwordEncoder The password encoder for encoding and verifying passwords.
     * @return The configured ReactiveAuthenticationManager.
     */
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(MapReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    /**
     * Sets up the security filter chain for HTTP requests in a reactive environment.
     * This method configures CSRF protection as disabled, permits unrestricted access to the login endpoint,
     * and requires authentication for other specified paths.
     *
     * @param http The ServerHttpSecurity to configure.
     * @param jwtService The service to manage JWT operations.
     * @return The security web filter chain after applying the configurations.
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, JwtService jwtService) {
        AuthenticationWebFilter jwtAuthFilter = new AuthenticationWebFilter(new JwtAuthenticationManager(jwtService));
        jwtAuthFilter.setServerAuthenticationConverter(new JWTAuthenticationConverter(jwtService));

        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/login").permitAll()
                .pathMatchers("/api/patients/**").authenticated()
                .pathMatchers("/api/notes/**").authenticated()
                .pathMatchers("/api/healthrisks/**").authenticated()
                .anyExchange().permitAll()
                .and()
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    /**
     * Provides a reactive user details service pre-loaded with user details.
     * This service is used for quick in-memory access to user information.
     *
     * @return A MapReactiveUserDetailsService containing predefined user details.
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user@gmail.com")
                .password(passwordEncoder().encode("password1"))
                .roles("USER")
                .build();
        UserDetails adminUser = User.withUsername("admin@gmail.com")
                .password(passwordEncoder().encode("password2"))
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user, adminUser);
    }


    /**
     * Provides a BCryptPasswordEncoder for password encoding and validation.
     * It's a strong cryptographic password encoder recommended for user passwords.
     *
     * @return A BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures a custom JWT authentication converter for web server applications.
     * This converter extracts authentication information from JWTs.
     *
     * @param jwtService The service that handles JWT operations.
     * @return A ServerAuthenticationConverter that uses JWTs for authentication.
     */
    @Bean
    public ServerAuthenticationConverter authenticationConverter(JwtService jwtService) {
        return new JWTAuthenticationConverter(jwtService);
    }
}

