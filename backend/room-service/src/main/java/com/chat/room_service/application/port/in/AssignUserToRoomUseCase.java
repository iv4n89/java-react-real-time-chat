package com.chat.room_service.application.port.in;

import java.util.UUID;

import com.chat.room_service.domain.model.Room;

public interface AssignUserToRoomUseCase {
    Room executeAssign(UUID userId, String username);
}
