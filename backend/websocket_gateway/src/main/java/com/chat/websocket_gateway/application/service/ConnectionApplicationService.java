package com.chat.websocket_gateway.application.service;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.chat.websocket_gateway.application.port.in.ManageConnectionUseCase;

@Component
public final class ConnectionApplicationService {
    private final ManageConnectionUseCase manageConnectionUseCase;

    public ConnectionApplicationService(ManageConnectionUseCase manageConnectionUseCase) {
        this.manageConnectionUseCase = manageConnectionUseCase;
    }

    public void connect(String sessionId, UUID userId, String username, UUID roomId) {
        manageConnectionUseCase.connection(sessionId, userId, username, roomId);
    }

    public void disconnect(String sessionId) {
        manageConnectionUseCase.disconnect(sessionId);
    }

    public int getConnectedUsersCount(UUID roomId) {
        return manageConnectionUseCase.getConnectedUsersCount(roomId);
    }
}
