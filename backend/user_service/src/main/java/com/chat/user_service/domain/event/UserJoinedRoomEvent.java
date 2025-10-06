package com.chat.user_service.domain.event;

import java.time.Instant;
import java.util.UUID;

public record UserJoinedRoomEvent(
    UUID userId,
    String username,
    UUID roomId,
    Instant occurredAt
) {
    public UserJoinedRoomEvent {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (roomId == null) {
            throw new IllegalArgumentException("roomId cannot be null");
        }
        if (occurredAt == null) {
            occurredAt = Instant.now();
        }
    }

    public static UserJoinedRoomEvent of(UUID userId, String username, UUID roomId) {
        return new UserJoinedRoomEvent(userId, username, roomId, Instant.now());
    }
}
