package com.chat.room_service.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.chat.room_service.application.port.in.AssignUserToRoomUseCase;
import com.chat.room_service.application.port.in.GetAvailableRoomsUseCase;
import com.chat.room_service.application.port.in.GetRoomUseCase;
import com.chat.room_service.application.port.in.RemoveUserfromRoomUseCase;
import com.chat.room_service.application.port.out.EventPublisherPort;
import com.chat.room_service.application.port.out.RoomPersistencePort;
import com.chat.room_service.domain.event.RoomClosedEvent;
import com.chat.room_service.domain.event.RoomCreatedEvent;
import com.chat.room_service.domain.event.RoomFullEvent;
import com.chat.room_service.domain.event.UserJoinedRoomEvent;
import com.chat.room_service.domain.event.UserLeftRoomEvent;
import com.chat.room_service.domain.model.Room;
import com.chat.room_service.domain.model.RoomId;
import com.chat.room_service.domain.model.RoomStatus;
import com.chat.room_service.domain.service.RoomDomainService;
import com.chat.room_service.shared.exception.RoomNotFoundException;
import com.chat.room_service.shared.exception.UserNotInRoomException;
import com.chat.room_service.shared.util.RoomNameGenerator;

public class RoomApplicationService
        implements AssignUserToRoomUseCase, RemoveUserfromRoomUseCase, GetRoomUseCase, GetAvailableRoomsUseCase {
    private final RoomPersistencePort roomPersistencePort;
    private final EventPublisherPort eventPublisherPort;
    private final RoomDomainService roomDomainService;

    public RoomApplicationService(RoomPersistencePort roomPersistencePort, EventPublisherPort eventPublisherPort,
            RoomDomainService roomDomainService) {
        this.roomPersistencePort = roomPersistencePort;
        this.eventPublisherPort = eventPublisherPort;
        this.roomDomainService = roomDomainService;
    }

    @Override
    public Room executeAssign(UUID userId, String username) {
        Optional<Room> availableRoom = roomDomainService.findAvailableRoom();

        Room room;
        boolean isNewRoom = false;

        if (availableRoom.isPresent()) {
            room = availableRoom.get();
            room.addMember(userId, username);
        } else {
            String baseName = RoomNameGenerator.generate();
            String uniqueName = roomDomainService.generateUniqueRoomName(baseName);

            room = Room.create(uniqueName);
            room.addMember(userId, username);
            isNewRoom = true;
        }

        Room savedRoom = roomPersistencePort.save(room);

        if (isNewRoom) {
            RoomCreatedEvent roomCreatedEvent = RoomCreatedEvent.of(savedRoom.getId().value(),
                    savedRoom.getName(),
                    savedRoom.getMaxUsers());
            
            eventPublisherPort.publishRoomCreated(roomCreatedEvent);
        }

        UserJoinedRoomEvent joinedEvent = UserJoinedRoomEvent.of(
            userId,
            username,
            savedRoom.getId().value(),
            savedRoom.getName(),
            savedRoom.getCurrentUsers()
        );

        eventPublisherPort.publishUserJoinedRoom(joinedEvent);

        if (savedRoom.isFull()) {
            RoomFullEvent fullEvent = RoomFullEvent.of(
                savedRoom.getId().value(), 
                savedRoom.getName()
            );

            eventPublisherPort.publishRoomFull(fullEvent);
        }

        return savedRoom;
    }

    @Override
    public List<Room> executeAvailable() {
        return roomPersistencePort.findByStatus(RoomStatus.OPEN);
    }

    @Override
    public Room executeGetRoom(UUID roomId) {
        RoomId id = new RoomId(roomId);
        return roomPersistencePort.findById(id)
            .orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    @Override
        public void executeRemoveUser(UUID userId) {
        Optional<Room> roomOpt = roomDomainService.findRoomByUserId(userId);

        if (roomOpt.isEmpty()) {
            throw new UserNotInRoomException(userId, null);
        }

        Room room = roomOpt.get();

        String username = room.getMembers().stream()
            .filter(m -> m.userId().equals(userId))
            .findFirst()
            .map(m -> m.username())
            .orElse("Unknown");

        room.removeMember(userId);

        Room savedRoom = roomPersistencePort.save(room);

        UserLeftRoomEvent leftEvent = UserLeftRoomEvent.of(
            userId, 
            username, 
            savedRoom.getId().value(), 
            savedRoom.getName(),
            savedRoom.getCurrentUsers()
        );

        eventPublisherPort.publishUserLeftRoom(leftEvent);

        if (savedRoom.isEmpty()) {
            RoomClosedEvent closedEvent = RoomClosedEvent.of(
                savedRoom.getId().value(),
                savedRoom.getName()
            );
            eventPublisherPort.publishRoomClosed(closedEvent);
        }
    }

    public List<Room> findAll() {
        return roomPersistencePort.findAll();
    }
}
