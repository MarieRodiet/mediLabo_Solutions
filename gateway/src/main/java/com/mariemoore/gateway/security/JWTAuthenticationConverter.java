package com.mariemoore.gateway.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JWTAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtService jwtService;

    public JWTAuthenticationConverter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getCookies().getFirst("token"))
                .map(cookie -> cookie.getValue())
                .filter(token -> jwtService.validateToken(token))
                .map(token -> {
                    String username = jwtService.extractUsername(token);
                    return (Authentication) new UsernamePasswordAuthenticationToken(username, token, null);
                });
    }
}

