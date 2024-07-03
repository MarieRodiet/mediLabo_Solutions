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

@Configuration
public class FeignClientConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }

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
