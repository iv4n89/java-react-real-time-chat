package com.chat.room_service.application.port.in;

import java.util.UUID;

import com.chat.room_service.domain.model.Room;

public interface GetRoomUseCase {
    /**
     * Get a room by its ID.
     * @param roomId the ID of the room to retrieve
     * @return the room with the specified ID
     * @throws com.chat.room_service.shared.exception.RoomNotFoundException if the room does not exist
     */
    Room executeGetRoom(UUID roomId);
}
