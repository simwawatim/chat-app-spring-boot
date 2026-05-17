package com.example.chat.user.DTOs;


import java.time.LocalDateTime;

public class MessageResponse {

    private Long id;

    private Long senderId;
    private String senderUsername;

    private Long receiverId;
    private String receiverUsername;

    private String content;

    private LocalDateTime timestamp;

    public MessageResponse(
            Long id,
            Long senderId,
            String senderUsername,
            Long receiverId,
            String receiverUsername,
            String content,
            LocalDateTime timestamp
    ) {
        this.id = id;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.receiverId = receiverId;
        this.receiverUsername = receiverUsername;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
