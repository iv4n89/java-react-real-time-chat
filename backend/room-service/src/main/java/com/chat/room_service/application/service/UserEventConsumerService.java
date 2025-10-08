package com.chat.room_service.application.service;

import java.util.UUID;

import com.chat.room_service.application.port.in.AssignUserToRoomUseCase;
import com.chat.room_service.application.port.in.RemoveUserfromRoomUseCase;

public class UserEventConsumerService {
    private final AssignUserToRoomUseCase assignUserToRoomUseCase;
    private final RemoveUserfromRoomUseCase removeUserfromRoomUseCase;

    public UserEventConsumerService(
        AssignUserToRoomUseCase assignUserToRoomUseCase,
        RemoveUserfromRoomUseCase removeUserfromRoomUseCase
    ) {
        this.assignUserToRoomUseCase = assignUserToRoomUseCase;
        this.removeUserfromRoomUseCase = removeUserfromRoomUseCase;
    }

    public void handleUserCreated(UUID userId, String username) {
        assignUserToRoomUseCase.executeAssign(userId, username);
    }

    public void handleUserDisconnected(UUID userUuid) {
        try {
            removeUserfromRoomUseCase.executeRemoveUser(userUuid);
        } catch (Exception e) {
            System.err.println("Error removing user " + userUuid + " from room: " + e.getMessage());
        }
    }
}
