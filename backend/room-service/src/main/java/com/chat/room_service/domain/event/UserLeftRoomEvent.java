package com.chat.room_service.domain.event;

import java.time.Instant;
import java.util.UUID;

public record UserLeftRoomEvent(
    UUID userId,
    String username,
    UUID roomId,
    String roomName,
    int remainingUsers,
    Instant occurredAt
) {
    public UserLeftRoomEvent {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (roomId == null) {
            throw new IllegalArgumentException("roomId cannot be null");
        }
        if (occurredAt == null) {
            occurredAt = Instant.now();
        }
    }

    public static UserLeftRoomEvent of(UUID userId, String username, UUID roomId, String roomName, int remainingUsers) {
        return new UserLeftRoomEvent(userId, username, roomId, roomName, remainingUsers, Instant.now());
    }
}
