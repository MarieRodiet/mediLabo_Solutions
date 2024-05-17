package com.mariemoore.gateway.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
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

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("test")
    public String test(@PathVariable String id, Model model, HttpServletRequest request){
        return "test";
    }

    @PostMapping("/login")
    public void createAuthenticationToken(@RequestBody Map<String, String> authenticationRequest, HttpServletResponse response) throws AuthenticationException, IOException {
        log.info("in authentication Request to get token");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.get("username"), authenticationRequest.get("password"))
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.get("username"));
            log.info("userDetails found with username : " + userDetails.getUsername());

            String jwt = jwtUtil.generateToken(userDetails);
            log.info("token : " + jwt);
            response.addHeader("Authorization", "Bearer " + jwt);
            response.getWriter().write("{\"token\": \"" + jwt + "\"}");
        } catch (Exception e) {
            log.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials");
        }
    }
}
