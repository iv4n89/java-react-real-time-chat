package com.chat.user_service.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

import com.chat.user_service.domain.model.User;

public record JoinChatResponse(
    UUID userId,
    String username,
    UUID roomId,
    String createdAt
) {
    public static JoinChatResponse fromDomain(User user) {
        return new JoinChatResponse(
            user.getId().value(), 
            user.getUsername(), 
            user.getRoomId(), 
            user.getCreatedAt().toString()
        );
    }
}
