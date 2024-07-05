package com.clientui.controller;

import com.clientui.security.AuthenticationService;
import com.clientui.security.TokenManager;
import jakarta.servlet.http.HttpServletRequest;
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
 * The AuthenticationController class is responsible for handling HTTP requests related to user login and logout.
 * This includes managing the authentication flow and setting or clearing authentication tokens.
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
     * Displays the login page to the user.
     *
     * @return the name of the login view template, which is "login".
     */
    @GetMapping("/login")
    public String login(){
        logger.info("login page");
        return "login";
    }

    /**
     * Processes the POST request to authenticate a user. If the authentication is successful,
     * a token is set in the response header and the user is redirected to the patients page.
     * If authentication fails, the user is informed of invalid credentials on the login page.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @param response The HttpServletResponse object to manipulate the HTTP response.
     * @param model The Model object to pass attributes to the view.
     * @return a string that indicates the view to which the user will be redirected or forwarded.
     */
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

    /**
     * Handles the GET request to log out a user. This method clears the authentication token and redirects
     * the user to the login page.
     *
     * @param request The HttpServletRequest object to read the HTTP request.
     * @param response The HttpServletResponse object to manipulate the HTTP response.
     * @return a string that indicates the view to which the user will be redirected.
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        logger.info("user logged out");
        tokenManager.logout(response);
        return "redirect:/login";
    }
}
