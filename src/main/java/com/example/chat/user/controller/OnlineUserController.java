package com.example.chat.user.controller;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

import com.example.chat.user.service.OnlineUserService;

import java.util.List;

@Controller
public class OnlineUserController {

    private final OnlineUserService service;

    public OnlineUserController(OnlineUserService service) {
        this.service = service;
    }

    @MessageMapping("/online-users")
    @SendTo("/topic/online-users")
    public List<String> getOnlineUsers() {
        return service.getUsers();
    }
}