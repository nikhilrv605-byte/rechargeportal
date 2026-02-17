package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscription")
@CrossOrigin
public class SubscriptionController {

    @PostMapping("/fake")
    public ResponseEntity<?> fakeSubscription(@RequestBody Map<String, Object> request) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Subscription Activated");
        response.put("subscriptionId", UUID.randomUUID().toString());

        return ResponseEntity.ok(response);
    }
}
