package com.mariemoore.gateway.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    /*@Autowired
    private JwtUtil jwtUtil*/

    @PostMapping("/login")
    public void createAuthenticationToken(@RequestBody Map<String, String> credentials, HttpServletResponse response) throws AuthenticationException, IOException {
        log.info("in authentication Request to get token");
        String username = null;
        String password = null;
        try {
            username = credentials.get("username");
            password = credentials.get("password");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.info("userDetails found with username : " + userDetails.getUsername());

            //String jwt = jwtUtil.generateToken(username);
            String jwt = "token";
                    log.info("token : " + jwt);
            response.addHeader("Authorization", "Bearer " + jwt);
            response.getWriter().write("{\"token\": \"" + jwt + "\"}");
        } catch (Exception e) {
            log.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials : " + username + password);
        }
    }
}

