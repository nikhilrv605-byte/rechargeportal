package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    // Forward all non-API, non-static routes to React's index.html
    @RequestMapping(value = {
        "/", "/login", "/register", "/recharge",
        "/subscription", "/payment", "/profile",
        "/about", "/contact", "/privacy", "/terms"
    })
    public String redirect() {
        return "forward:/index.html";
    }
}