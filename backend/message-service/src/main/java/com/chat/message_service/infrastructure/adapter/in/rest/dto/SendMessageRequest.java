package com.chat.message_service.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

public record SendMessageRequest(
    UUID roomId,
    UUID userId,
    String username,
    String content
) {
    public SendMessageRequest {
        if (roomId == null) {
            throw new IllegalArgumentException("roomId cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username cannot be null or blank");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("content cannot be null or blank");
        }
    }
}
