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

@Slf4j
@RestController
public class AuthenticationController {

    @Autowired
    private ReactiveAuthenticationManager reactiveAuthenticationManager;

    @Autowired
    private MapReactiveUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> createAuthenticationToken(@RequestBody Map<String, String> credentials) {
        log.info("Received authentication request");

        String username = credentials.get("username");
        String password = credentials.get("password");

        return userDetailsService.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .flatMap(userDetails -> {
                    Authentication authToken = new UsernamePasswordAuthenticationToken(username, password);
                    return reactiveAuthenticationManager.authenticate(authToken)
                            .flatMap(authentication -> {
                                if (authentication.isAuthenticated()) {
                                    String jwt = jwtService.generateToken(userDetails.getUsername());
                                    return Mono.just(ResponseEntity.ok(jwt));
                                } else {
                                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
                                }
                            })
                            .onErrorResume(AuthenticationException.class, e -> {
                                log.error("Authentication failed: {}", e.getMessage());
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
                            });
                });
    }
}
