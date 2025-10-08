package com.chat.room_service.domain.event;

import java.time.Instant;
import java.util.UUID;

public record RoomCreatedEvent(
    UUID roomId,
    String roomName,
    int maxUsers,
    Instant occurredAt
) {
    public RoomCreatedEvent {
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

    public static RoomCreatedEvent of(UUID roomId, String roomName, int maxUsers) {
        return new RoomCreatedEvent(roomId, roomName, maxUsers, Instant.now());
    }
}
