package com.chat.room_service.shared.exception;

import java.util.UUID;

public class RoomFullException extends RuntimeException {
    public RoomFullException(UUID roomId) {
        super("Room with ID " + roomId + " is full.");
    }

    public RoomFullException(String roomName) {
        super("Room with name '" + roomName + "' is full.");
    }
}
