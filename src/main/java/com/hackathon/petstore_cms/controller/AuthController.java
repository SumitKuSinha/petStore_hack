package com.hackathon.petstore_cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hackathon.petstore_cms.dto.UserDto;
import com.hackathon.petstore_cms.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Show the custom login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Show the registration page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    // Handle the registration form
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDto userDto) {
        userService.saveUser(userDto);
        return "redirect:/login?success"; // Go to login with a success msg
    }

    // --- NEW METHOD FOR LOGOUT FIX ---
    @GetMapping("/logout-success")
    public String logoutSuccessPage() {
        // This tells Spring to load the 'logout-reload.html' template
        // This page contains the JavaScript that forces a clean redirect to "/"
        return "logout-reload"; 
    }
}