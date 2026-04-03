package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.reposiotry.UserRepository;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // 🔹 REGISTER
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        if (!request.password.equals(request.confirmPassword)) {
            return "Passwords do not match";
        }

        if (userRepository.findByEmail(request.email).isPresent()) {
            return "Email already registered";
        }

        User user = new User();
        user.setName(request.name);
        user.setEmail(request.email);
        user.setPassword(request.password);

        userRepository.save(user);
        return "Registration successful";
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User request) {

        Map<String, Object> response = new HashMap<>();

         Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isPresent() &&
            user.get().getPassword().equals(request.getPassword())) {
        	
            response.put("message", "Login successful");
            response.put("user", user.get());
            response.put("success", true);

            return ResponseEntity.ok(response);
        }

        response.put("message", "Invalid email or password");
        response.put("user", null);
        response.put("success", false);

        return ResponseEntity.status(401).body(response);
    }
    
   
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        Object principal = authentication.getPrincipal();
        Map<String, Object> response = new HashMap<>();

        // 🔹 Google OAuth login
        if (principal instanceof OAuth2User oauthUser) {

            response.put("name", oauthUser.getAttribute("name"));
            response.put("email", oauthUser.getAttribute("email"));
            response.put("provider", "GOOGLE");

            return ResponseEntity.ok(response);
        }

        // 🔹 Normal login (if using UserDetails later)
        response.put("username", principal.toString());
        response.put("provider", "LOCAL");

        return ResponseEntity.ok(response);
    }
}
