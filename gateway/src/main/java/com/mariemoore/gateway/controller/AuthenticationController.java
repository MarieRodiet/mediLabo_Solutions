package com.mariemoore.gateway.controller;

import com.mariemoore.gateway.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Controller for handling authentication requests in a reactive application setup.
 * This class uses JWT for generating tokens once authentication is successful.
 */
@Slf4j
@RestController
public class AuthenticationController {

    @Autowired
    private ReactiveAuthenticationManager reactiveAuthenticationManager;

    @Autowired
    private MapReactiveUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    /**
     * Processes POST requests to /login by authenticating user credentials and issuing a JWT upon successful authentication.
     *
     * @param credentials A map containing "username" and "password" keys.
     * @return A Mono that publishes a ResponseEntity containing the JWT or an error message.
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> createAuthenticationToken(@RequestBody Map<String, String> credentials) {
        log.info("Received authentication request");

        String username = credentials.get("username");
        String password = credentials.get("password");

        // Attempt to find user by username and handle the result reactively.
        return userDetailsService.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .flatMap(userDetails -> {
                    Authentication authToken = new UsernamePasswordAuthenticationToken(username, password);
                    // Authenticate the token and process the result.
                    return reactiveAuthenticationManager.authenticate(authToken)
                            .flatMap(authentication -> {
                                if (authentication.isAuthenticated()) {
                                    // Generate JWT if authentication is successful.
                                    String jwt = jwtService.generateToken(userDetails.getUsername());
                                    return Mono.just(ResponseEntity.ok(jwt));
                                } else {
                                    // Respond with unauthorized status if authentication fails.
                                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
                                }
                            })
                            // Handle authentication errors explicitly and log the exception.
                            .onErrorResume(AuthenticationException.class, e -> {
                                log.error("Authentication failed: {}", e.getMessage());
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
                            });
                });
    }
}
