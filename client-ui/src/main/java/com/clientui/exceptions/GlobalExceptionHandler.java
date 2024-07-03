package com.clientui.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        return new ModelAndView("redirect:/login");
    }

    @ExceptionHandler(CustomFeignErrorDecoder.UnauthorizedException.class)
    public ModelAndView handleUnauthorizedException(HttpServletRequest request) {
        return new ModelAndView("redirect:/login");
    }
}
