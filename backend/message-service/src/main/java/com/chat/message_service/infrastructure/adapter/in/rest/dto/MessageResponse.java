package com.chat.message_service.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

import com.chat.message_service.domain.model.Message;

public record MessageResponse(
    UUID messageId,
    UUID roomId,
    UUID userId,
    String username,
    String content,
    String sentAt
) {
    public static MessageResponse fromDomain(Message message) {
        return new MessageResponse(
            message.getId().getValue(), 
            message.getRoomId(), 
            message.getUserId(), 
            message.getUsername(), 
            message.getContentAsString(), 
            message.getSentAt().toString()
        );
    }
}
