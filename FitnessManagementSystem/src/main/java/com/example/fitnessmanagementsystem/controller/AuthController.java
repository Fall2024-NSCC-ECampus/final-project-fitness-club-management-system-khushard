package com.example.fitnessmanagementsystem.controller;

import com.example.fitnessmanagementsystem.model.User;
import com.example.fitnessmanagementsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller for managing user authentication.
 * Handles login and logout functions.
 * */
@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    /**
     * Displays the login page.
     *
     * @return The name of the view for the login page.
     * */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    /**
     * Handles login functionality for users.
     * Validates the user credentials and redirects them to the proper dashboard based on
     * their role (ADMIN, TRAINER OR MEMBER).
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @param session The HTTP session to store all the user details.
     * @param redirectAttributes Attributes to pass error messages when redirecting.
     * @return Redirects to the proper dashboard based on role, or login page is auth fails.
     * */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        try {
            // Debugging log for login attempts
            System.out.println("Login attempt for username: " + username);

            User user = userService.login(username, password);

            // Debugging log for user details
            System.out.println("User found: " + user.getUsername());
            System.out.println("User ID: " + user.getId());
            System.out.println("User Role: " + user.getRole());

            // Stores user details in the session
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getRole());

            // Debugging log for session attributes.
            System.out.println("Session userId: " + session.getAttribute("userId"));
            System.out.println("Session userRole: " + session.getAttribute("userRole"));

            // Redirects based on user role
            String redirectUrl = switch (user.getRole()) {
                case "ADMIN" -> "redirect:/admin/dashboard";
                case "TRAINER" -> "redirect:/trainer/dashboard";
                case "MEMBER" -> "redirect:/member/dashboard";
                default -> "redirect:/login";
            };

            // Debugging log for the redirection URL
            System.out.println("Redirecting to: " + redirectUrl);

            return redirectUrl;
        } catch (RuntimeException e) {
            // Debugging log for login errors
            System.out.println("Login error: " + e.getMessage());

            // Pass error message to the login page
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }



    /**
     * Handles logging out of a session.
     * Invalidates the session and redirects to the login page.
     * */
    @GetMapping
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


}
