package com.chat.websocket_gateway.application.port.in;

import java.util.UUID;

public interface ManageConnectionUseCase {
    void connection(String sessionId, UUID userId, String username, UUID roomId);
    void disconnect(String sessionId);
    int getConnectedUsersCount(UUID roomId);
}
