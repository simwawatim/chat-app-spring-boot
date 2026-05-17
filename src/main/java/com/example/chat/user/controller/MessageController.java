package com.example.chat.user.controller;


import com.example.chat.response.ApiResponse;
import com.example.chat.user.DTOs.ChatPageResponse;
import com.example.chat.user.DTOs.MessageRequest;
import com.example.chat.user.DTOs.MessageResponse;
import com.example.chat.user.service.MessageService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    // SEND MESSAGE
    @PostMapping
    public ApiResponse<MessageResponse> send(
            @Valid @RequestBody MessageRequest req
    ) {

        return new ApiResponse<>(
                "success",
                "Message sent successfully",
                service.send(req),
                201
        );
    }

    // GET CHAT
    @GetMapping
    public ApiResponse<ChatPageResponse> getChat(
            @RequestParam Long user1,
            @RequestParam Long user2,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        return new ApiResponse<>(
                "success",
                "Chat fetched successfully",
                service.getChat(user1, user2, page, size),
                200
        );
    }
}