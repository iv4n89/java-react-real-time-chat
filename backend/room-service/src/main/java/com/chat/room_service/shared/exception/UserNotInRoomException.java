package com.chat.room_service.shared.exception;

import java.util.UUID;

public class UserNotInRoomException extends RuntimeException {
    public UserNotInRoomException(UUID userId, UUID roomId) {
        super("User with ID " + userId + " is not in room with ID " + roomId + ".");
    }
}
