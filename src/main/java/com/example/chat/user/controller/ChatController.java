package com.example.chat.user.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.chat.user.chat.ChatMessage;

@Controller
public class ChatController {

    private final SimpMessagingTemplate template;

    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat.private")
    public void send(ChatMessage msg) {

        template.convertAndSendToUser(
                msg.getReceiver(),
                "/queue/messages",
                msg
        );

        template.convertAndSendToUser(
                msg.getSender(),
                "/queue/messages",
                msg
        );
    }
}