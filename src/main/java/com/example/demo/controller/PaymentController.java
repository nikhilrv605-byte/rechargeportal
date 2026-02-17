package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payment.PaymentService;
import com.razorpay.Order;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService razorpayService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestParam int amount) throws Exception {

        Order order = razorpayService.createOrder(amount);

        return ResponseEntity.ok(order.toString());
    }
}
