package com.clientui.controller;

import com.clientui.security.AuthenticationService;
import com.clientui.security.TokenManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * The AuthenticationController class is responsible for handling HTTP requests related to registration and login.
 */
@Controller
@Slf4j
public class AuthenticationController {
    private static final Logger logger = getLogger(AuthenticationController.class);
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenManager tokenManager;

    /**
     * Handles GET requests to the "/login" endpoint, allowing users to login.
     *
     * @return the view name "/login".
     */
    @GetMapping("/login")
    public String login(){
        logger.info("login page");
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpServletResponse response,
                               Model model) {
        String authToken = authenticationService.authenticate(username, password);

        if (authToken != null) {
            tokenManager.setToken(authToken, response);
            logger.info("User logged in successfully: {}", username);
            response.addHeader("Authorization", "Bearer " + authToken);
            return "redirect:/patients";
        } else {
            logger.warn("Invalid credentials provided for user: {}", username);
            model.addAttribute("loginError", "Invalid credentials");
            return "login";
        }
    }
}
