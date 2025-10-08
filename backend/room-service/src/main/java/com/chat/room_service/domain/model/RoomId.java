package com.chat.room_service.domain.model;

import java.util.UUID;

public record RoomId(UUID value) {
    public RoomId {
        if (value == null) {
            throw new IllegalArgumentException("RoomId cannot be bull");
        }
    }

    public static RoomId generate() {
        return new RoomId(UUID.randomUUID());
    }

    public static RoomId of(String value) {
        return new RoomId(UUID.fromString(value));
    }

    @Override
    public final String toString() {
        return value.toString();
    }
}
