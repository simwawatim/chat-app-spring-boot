package com.example.chat.user.service;

import com.example.chat.exception.ResourceNotFoundException;
import com.example.chat.user.DTOs.ChatPageResponse;
import com.example.chat.user.DTOs.MessageRequest;
import com.example.chat.user.DTOs.MessageResponse;
import com.example.chat.user.entity.Message;
import com.example.chat.user.entity.User;
import com.example.chat.user.repository.MessageRepository;
import com.example.chat.user.repository.UserRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository repo;
    private final UserRepository userRepo;

    public MessageService(MessageRepository repo,
                          UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    // SEND MESSAGE
    public MessageResponse send(MessageRequest req) {

        User sender = userRepo.findById(req.getSenderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sender not found"));

        User receiver = userRepo.findById(req.getReceiverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Receiver not found"));

        Message msg = new Message();

        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setContent(req.getContent());

        return map(repo.save(msg));
    }

    // GET CHAT
    public ChatPageResponse getChat(Long user1,
                                    Long user2,
                                    int page,
                                    int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("timestamp").descending()
        );

        Page<Message> result =
                repo.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
                        user1,
                        user2,
                        user1,
                        user2,
                        pageable
                );

        return new ChatPageResponse(
                result.getContent()
                        .stream()
                        .map(this::map)
                        .toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    // MAP RESPONSE
    private MessageResponse map(Message msg) {

        return new MessageResponse(
                msg.getId(),

                msg.getSender().getId(),
                msg.getSender().getUsername(),

                msg.getReceiver().getId(),
                msg.getReceiver().getUsername(),

                msg.getContent(),
                msg.getTimestamp()
        );
    }
}