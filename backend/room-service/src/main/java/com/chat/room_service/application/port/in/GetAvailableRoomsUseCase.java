package com.chat.room_service.application.port.in;

import java.util.List;

import com.chat.room_service.domain.model.Room;

public interface GetAvailableRoomsUseCase {
    /**
     * Get all available rooms.
     * @return a list of available rooms
     */
    List<Room> executeAvailable();
}
