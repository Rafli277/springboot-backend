package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // Tambahkan ini
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "Backend is running!";
    }
}