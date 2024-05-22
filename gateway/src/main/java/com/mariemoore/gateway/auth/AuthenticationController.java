package com.mariemoore.gateway.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody Map<String, String> credentials) throws IOException {
        log.info("in authentication Request to get token");
        String username;
        String password;
        try {
            username = credentials.get("username");
            password = credentials.get("password");

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(userDetails != null){
                log.info("userDetails found with username : " + userDetails.getUsername());
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                String jwt = jwtUtil.generateToken(username);
                return ResponseEntity.ok(jwt);
            }
            else {
                log.error("user does not exist");
                return ResponseEntity.status(401).body("bad credentials");
            }


        } catch (UsernameNotFoundException | BadCredentialsException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(401).body("username not found");
        }
    }
}

