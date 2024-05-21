package com.mariemoore.gateway.configuration;


import com.mariemoore.gateway.auth.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.ArrayList;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
// Indicates that this class is a Spring component and should be automatically detected and registered during component scanning.
public class JwtRequestFilter extends OncePerRequestFilter { // Custom filter for processing JWT authentication tokens on each HTTP request.

    private final JwtUtil jwtTokenUtil; // Instance of JwtUtil for working with JWT tokens.

    public JwtRequestFilter(JwtUtil jwtTokenUtil) { // Constructor to inject JwtUtil dependency.
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        log.info("request: " + request);
        log.info("jwtTokenUtil.getSecretKey() : " + jwtTokenUtil.getSecretKey());
        String requestPath = request.getServletPath();
        log.info("requestPath: " + requestPath);
        if (requestPath.equals("/login")) {
            log.info("inside login ");
            return;
        }
        log.info("NOT inside login ");
        // If the request was not for "/login", proceed with token validation
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtTokenUtil.getSecretKey())
                        .parseClaimsJws(token)
                        .getBody();
                String username = claims.getSubject();

                if (username != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
    }
}
