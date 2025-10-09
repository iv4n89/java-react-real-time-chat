package com.chat.message_service.domain.service;

import java.util.List;
import java.util.UUID;

import com.chat.message_service.domain.model.Message;
import com.chat.message_service.domain.repository.MessageRepository;

public class MessageDomainService {
    private final MessageRepository messageRepository;

    public MessageDomainService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessageHistory(UUID roomId) {
        return messageRepository.findByRoomId(roomId);
    }

    public List<Message> getRecentMessages(UUID roomId, int limit) {
        if (limit <= 0) {
            limit = 50;
        }
        if (limit > 200) {
            limit = 200;
        }
        return messageRepository.findTopNByRoomIdOrderBySentAtDesc(roomId, limit);
    }
}
