package com.clientui.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Component
public class TokenManager {

    public void setToken(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("authToken", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // Make the cookie accessible only via HTTP (not JavaScript)
        cookie.setMaxAge(3600); // Set the cookie expiration time in seconds (e.g., 1 hour)
        response.addCookie(cookie);
    }

    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("authToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null; // Token not found in cookies
    }

    public void addTokenToHeaders(HttpServletRequest request, HttpHeaders headers) {
        String token = getToken(request);
        if (token != null) {
            headers.add("Authorization", "Bearer " + token);
        }
    }
}
