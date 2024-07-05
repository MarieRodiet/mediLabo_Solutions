package com.clientui.security;

import com.clientui.exceptions.CustomFeignErrorDecoder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Configuration class for Feign clients used in the Client UI application.
 * This configuration includes an error decoder and a request interceptor to enhance the functionality
 * of Feign clients throughout the application.
 */
@Configuration
public class FeignClientConfiguration {

    /**
     * Provides a custom Feign ErrorDecoder to handle errors specifically tailored to the needs of this application.
     * The custom decoder enhances error handling by applying application-specific logic to interpret errors
     * returned from calls made through Feign clients.
     *
     * @return An instance of ErrorDecoder.
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }

    /**
     * Provides a Feign RequestInterceptor that adds an authorization token to the headers of outgoing requests.
     * The token is extracted from the cookies of the incoming request. This interceptor ensures that the
     * microservices architecture maintains a seamless authentication flow across service boundaries.
     *
     * @return An instance of RequestInterceptor.
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attrs != null) {
                    HttpServletRequest request = attrs.getRequest();
                    String token = getTokenFromCookies(request);
                    if (token != null) {
                        template.header("Authorization", "Bearer " + token);
                    }
                }
            }

            /**
             * Retrieves the authentication token from the cookies of the HttpServletRequest.
             * Specifically, it searches for a cookie named "authToken".
             *
             * @param request The HttpServletRequest from which to extract the token.
             * @return The token value if found, otherwise null.
             */
            private String getTokenFromCookies(HttpServletRequest request) {
                if (request.getCookies() != null) {
                    for (Cookie cookie : request.getCookies()) {
                        if ("authToken".equals(cookie.getName())) {
                            return cookie.getValue();
                        }
                    }
                }
                return null;
            }
        };
    }
}
