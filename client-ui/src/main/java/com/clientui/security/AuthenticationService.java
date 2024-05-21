package com.clientui.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AuthenticationService {

    private final RestTemplate restTemplate;

    @Autowired
    public AuthenticationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String authenticate(String username, String password) {
        String loginUrl = "http://localhost:9002/login";

        // Prepare the request body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";


        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            // Send the POST request to the gateway's /login endpoint
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(loginUrl, requestEntity, String.class);
            // Return the authentication token from the response body
            return responseEntity.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error(e.getMessage());
            return null;

        }
    }
}
