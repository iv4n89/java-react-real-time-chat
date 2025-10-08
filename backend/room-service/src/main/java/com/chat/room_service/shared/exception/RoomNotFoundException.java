package com.chat.room_service.shared.exception;

import java.util.UUID;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(UUID roomId) {
        super("Room with ID " + roomId + " not found.");
    }

    public RoomNotFoundException(String roomName) {
        super("Room with name '" + roomName + "' not found.");
    }
}
