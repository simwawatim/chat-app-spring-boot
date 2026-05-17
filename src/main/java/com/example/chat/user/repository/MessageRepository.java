package com.example.chat.user.repository;

import com.example.chat.user.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
            Long sender1,
            Long receiver1,
            Long sender2,
            Long receiver2,
            Pageable pageable
    );
}