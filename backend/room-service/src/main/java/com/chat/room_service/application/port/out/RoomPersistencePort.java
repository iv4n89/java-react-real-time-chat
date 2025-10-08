package com.chat.room_service.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.chat.room_service.domain.model.Room;
import com.chat.room_service.domain.model.RoomId;
import com.chat.room_service.domain.model.RoomStatus;

public interface RoomPersistencePort {
    Room save(Room room);
    Optional<Room> findById(RoomId roomId);
    Optional<Room> findByName(String name);
    List<Room> findByStatus(RoomStatus status);
    Optional<Room> findFirstByStatusOrderByCreatedAtAsc(RoomStatus status);
    Optional<Room> findByMemberUserId(UUID userId);
    List<Room> findAll();
    void deleteById(RoomId roomId);
    boolean existsByName(String name);
}
