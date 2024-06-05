package com.mariemoore.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class JWTAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtService jwtService;

    public JWTAuthenticationConverter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String token = extractTokenFromRequest(exchange);
        if (token != null) {
            log.info("Extracted token: {}", token);
            if (jwtService.validateToken(token)) {
                String username = jwtService.extractUsername(token);
                log.info("Token valid. Authenticated user: {}", username);
                return Mono.just(new UsernamePasswordAuthenticationToken(username, token, null));
            } else {
                log.warn("Invalid token.");
            }
        } else {
            log.warn("Token not found in request.");
        }
        return Mono.empty();
    }

    private String extractTokenFromRequest(ServerWebExchange exchange) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
