package com.example.bookstoreapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the Bookstore API! This application is working fine. ";
    }
}
