package com.chat.user_service.domain.event;

import java.time.Instant;
import java.util.UUID;


public record UserCreatedEvent(
    UUID userId,
    String username,
    Instant occurredAt
) {
    public UserCreatedEvent {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (occurredAt == null) {
            occurredAt = Instant.now();
        }
    }

    public static UserCreatedEvent of(UUID userId, String username) {
        return new UserCreatedEvent(userId, username, Instant.now());
    }
}
