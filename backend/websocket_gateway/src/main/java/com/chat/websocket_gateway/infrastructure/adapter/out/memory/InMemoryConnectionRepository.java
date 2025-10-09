package com.chat.websocket_gateway.infrastructure.adapter.out.memory;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.chat.websocket_gateway.domain.model.WebSocketConnection;
import com.chat.websocket_gateway.domain.repository.ConnectionRepository;

@Component
public class InMemoryConnectionRepository implements ConnectionRepository {
    private final Map<String, WebSocketConnection> connections = new ConcurrentHashMap<>();

    @Override
    public void addConnection(WebSocketConnection connection) {
        connections.put(connection.getSessionId(), connection);
        System.out.println("Total connections: " + connections.size());
    }

    @Override
    public Optional<WebSocketConnection> findBySessionId(String sessionId) {
        return Optional.ofNullable(connections.get(sessionId));
    }

    @Override
    public Set<WebSocketConnection> findByRoomId(UUID roomId) {
        return connections.values().stream()
            .filter(conn -> conn.getRoomId().equals(roomId))
            .collect(Collectors.toSet());
    }

    @Override
    public Set<WebSocketConnection> findByUserId(UUID userId) {
        return connections.values().stream()
            .filter(conn -> conn.getUserId().equals(userId))
            .collect(Collectors.toSet());
    }

    @Override
    public int countConnectionsByRoomId(UUID roomId) {
        return (int) connections.values().stream()
            .filter(conn -> conn.getRoomId().equals(roomId))
            .count();
    }

    @Override
    public Set<WebSocketConnection> findAll() {
        return new HashSet<>(connections.values());
    }

    @Override
    public boolean isUserConnected(UUID userId) {
        return connections.values().stream()
            .anyMatch(conn -> conn.getUserId().equals(userId));
    }

    @Override
    public void removeConnection(String sessionId) {
        connections.remove(sessionId);
        System.out.println("Total connections: " + connections.size());
    }
}
