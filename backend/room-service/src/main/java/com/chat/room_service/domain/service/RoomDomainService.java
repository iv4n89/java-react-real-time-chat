package com.chat.room_service.domain.service;

import java.util.Optional;
import java.util.UUID;

import com.chat.room_service.domain.model.Room;
import com.chat.room_service.domain.model.RoomStatus;
import com.chat.room_service.domain.repository.RoomRepository;

public class RoomDomainService {
    private final RoomRepository roomRepository;

    public RoomDomainService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Optional<Room> findAvailableRoom() {
        return roomRepository.findFirstByStatusOrderByCreatedAtAsc(RoomStatus.OPEN)
            .filter(Room::hasSpace);
    }

    public boolean isRoomNameAvailable(String name) {
        return !roomRepository.existsByName(name);
    }

    public String generateUniqueRoomName(String baseName) {
        if (isRoomNameAvailable(baseName)) {
            return baseName;
        }

        int suffix = 1;
        String candidateName;
        do {
            candidateName = baseName + "-" + suffix;
            suffix++;
        } while (!isRoomNameAvailable(candidateName) && suffix < 1000);

        if (suffix >= 1000) {
            throw new IllegalStateException("Unable to generate unique room name");
        }

        return candidateName;
    }

    public Optional<Room> findRoomByUserId(UUID userId) {
        return roomRepository.findByMemberUserId(userId);
    }
}
