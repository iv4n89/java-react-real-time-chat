package com.chat.message_service.domain.event;

import java.time.Instant;
import java.util.UUID;

public record MessageSentEvent(
    UUID messageId,
    UUID roomId,
    UUID userId,
    String username,
    String content,
    Instant sentAt
) {
    public MessageSentEvent {
        if (messageId == null) {
            throw new IllegalArgumentException("messageId cannot be null");
        }
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
        if (sentAt == null) {
            sentAt = Instant.now();
        }
    }

    public static MessageSentEvent of(
        UUID messageId,
        UUID roomId,
        UUID userId,
        String username,
        String content
    ) {
        return new MessageSentEvent(messageId, roomId, userId, username, content, Instant.now());
    }
}
