package com.mariemoore.gateway.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtService jwtService;


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .filter(auth -> jwtService.validateToken(auth.getCredentials().toString()))
                .map(auth -> {
                    String token = auth.getCredentials().toString();
                    String username = jwtService.extractUsername(token);
                    return (Authentication) new UsernamePasswordAuthenticationToken(username, token, null);
                })
                .switchIfEmpty(Mono.error(new JwtAuthenticationException("Invalid token.")));
    }
}

