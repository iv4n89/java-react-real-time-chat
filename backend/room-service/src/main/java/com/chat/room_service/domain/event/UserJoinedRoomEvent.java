package com.chat.room_service.domain.event;

import java.time.Instant;
import java.util.UUID;

public record UserJoinedRoomEvent(
    UUID userId,
    String username,
    UUID roomId,
    String roomName,
    int currentUsers,
    Instant occurredAt
) {
    public UserJoinedRoomEvent {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (roomId == null) {
            throw new IllegalArgumentException("roomId cannot be null");
        }
        if (roomName == null || roomName.isBlank()) {
            throw new IllegalArgumentException("roomName cannot be null or blank");
        }
        if (occurredAt == null) {
            occurredAt = Instant.now();
        }
    }

    public static UserJoinedRoomEvent of(UUID userId, String username, UUID roomId, String roomName, int currentUsers) {
        return new UserJoinedRoomEvent(userId, username, roomId, roomName, currentUsers, Instant.now());
    }
}
