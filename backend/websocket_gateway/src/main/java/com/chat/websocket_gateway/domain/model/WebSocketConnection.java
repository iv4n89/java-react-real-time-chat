package com.chat.websocket_gateway.domain.model;

import java.time.Instant;
import java.util.UUID;

public class WebSocketConnection {
    private final String sessionId;
    private final UUID userId;
    private final String username;
    private final UUID roomId;
    private final Instant connectedAt;

    private WebSocketConnection(String sessionId, UUID userId, String username, UUID roomId, Instant connectedAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.username = username;
        this.roomId = roomId;
        this.connectedAt = connectedAt;
    }

    public static WebSocketConnection create(String sessionId, UUID userId, String username, UUID roomId) {
        validateSessionId(sessionId);
        validateUserId(userId);
        validateUsername(username);
        validateRoomId(roomId);

        return new WebSocketConnection(sessionId, userId, username, roomId, Instant.now());
    }

    private static void validateSessionId(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            throw new IllegalArgumentException("Session ID cannot be null or blank");
        }
    }

    private static void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }

    private static void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
    }

    private static void validateRoomId(UUID roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
    }

    // Getters
    public String getSessionId() {
        return sessionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public Instant getConnectedAt() {
        return connectedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WebSocketConnection that = (WebSocketConnection) obj;
        return sessionId.equals(that.sessionId);
    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }

    @Override
    public String toString() {
        return "WebSocketConnection{" +
                "sessionId='" + sessionId + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", roomId=" + roomId +
                ", connectedAt=" + connectedAt +
                '}';
    }
}
