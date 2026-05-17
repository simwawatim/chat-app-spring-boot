package com.example.chat.ws;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

import com.example.chat.user.service.OnlineUserService;

import java.util.UUID;

@Component
public class WebSocketEventListener {

    private final OnlineUserService service;
    private final SimpMessagingTemplate template;

    public WebSocketEventListener(OnlineUserService service,
                                  SimpMessagingTemplate template) {
        this.service = service;
        this.template = template;
    }

    @EventListener
    public void handleConnect(SessionConnectEvent event) {

        String email = event.getUser() != null
                ? event.getUser().getName()
                : "guest_" + UUID.randomUUID();

        service.addUser(email);

        broadcast();
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {

        String email = event.getUser() != null
                ? event.getUser().getName()
                : null;

        if (email != null) {
            service.removeUser(email);
        }

        broadcast();
    }

    private void broadcast() {
        template.convertAndSend("/topic/online-users", service.getUsers());
    }
}