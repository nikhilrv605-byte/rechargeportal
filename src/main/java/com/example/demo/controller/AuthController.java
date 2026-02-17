package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
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
}
