package com.chat.room_service.domain.event;

import java.time.Instant;
import java.util.UUID;

public record RoomClosedEvent(
    UUID roomId,
    String roomName,
    Instant occurredAt
) {
    public RoomClosedEvent {
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

    public static RoomClosedEvent of(UUID roomId, String roomName) {
        return new RoomClosedEvent(roomId, roomName, Instant.now());
    }
}
