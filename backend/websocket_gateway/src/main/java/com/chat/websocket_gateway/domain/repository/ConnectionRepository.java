package com.chat.websocket_gateway.domain.repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.chat.websocket_gateway.domain.model.WebSocketConnection;

public interface ConnectionRepository {
    void addConnection(WebSocketConnection connection);
    void removeConnection(String sessionId);
    Optional<WebSocketConnection> findBySessionId(String sessionId);
    Set<WebSocketConnection> findByRoomId(UUID roomId);
    Set<WebSocketConnection> findByUserId(UUID userId);
    int countConnectionsByRoomId(UUID roomId);
    Set<WebSocketConnection> findAll();
    boolean isUserConnected(UUID userId);
}
