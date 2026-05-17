package com.example.chat.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/status")
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("time", LocalDateTime.now());
        response.put("service", "User Service");

        return response;
    }
}