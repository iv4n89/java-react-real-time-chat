package com.chat.user_service.domain.event;

import java.time.Instant;
import java.util.UUID;

public record UserDisconnectedEvent(
    UUID userId,
    String username,
    UUID roomId,
    Instant occurredAt
) {
    public UserDisconnectedEvent {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (roomId == null) {
            throw new IllegalArgumentException("RoomId cannot be null");
        }
        if (occurredAt == null) {
            occurredAt = Instant.now();
        }
    }

    public static UserDisconnectedEvent of(UUID userId, String username, UUID roomId) {
        return new UserDisconnectedEvent(userId, username, roomId, Instant.now());
    }
}
