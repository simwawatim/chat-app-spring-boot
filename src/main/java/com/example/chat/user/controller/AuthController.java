package com.example.chat.user.controller;

import com.example.chat.response.ApiResponse;
import com.example.chat.user.DTOs.LoginRequestDTO;
import com.example.chat.user.DTOs.LoginResponseDTO;
import com.example.chat.user.DTOs.UserRequestDTO;
import com.example.chat.user.DTOs.UserResponseDTO;
import com.example.chat.user.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {

        LoginResponseDTO response = service.login(dto);

        return new ApiResponse<>(
                "success",
                "Login successful",
                response,
                200
        );
    }

    @PostMapping("/register")
    public ApiResponse<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO dto) {

        return new ApiResponse<>(
                "success",
                "User created successfully",
                service.create(dto),
                201
        );
    }



}