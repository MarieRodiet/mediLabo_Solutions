package com.clientui.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


@Component
public class TokenManager {

    private static final String COOKIE_NAME = "authToken";
    private static final String COOKIE_PATH = "/";
    private static final int COOKIE_MAX_AGE = 3600;

    public void setToken(String token, HttpServletResponse response) {
        try {
            String encodedToken = URLEncoder.encode(token, "UTF-8");
            Cookie cookie = new Cookie(COOKIE_NAME, encodedToken);
            cookie.setPath(COOKIE_PATH);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(COOKIE_MAX_AGE);
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace(); // Log or handle the exception as needed
        }
    }

    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace(); // Log or handle the exception as needed
                    }
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
