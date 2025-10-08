package com.chat.room_service.domain.model;

import java.time.Instant;
import java.util.UUID;

public record RoomMember(
    UUID userId,
    String username,
    Instant joinedAt
) {
    public RoomMember {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (joinedAt == null) {
            joinedAt = Instant.now();
        }
    }

    public static RoomMember of(UUID userId, String username) {
        return new RoomMember(userId, username, Instant.now());
    }
}
