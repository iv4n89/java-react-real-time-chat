package com.chat.websocket_gateway.domain.model;

import java.time.Instant;
import java.util.UUID;

public record ChatMessage(
    UUID messageId,
    UUID roomId,
    UUID userId,
    String username,
    String content,
    Instant sentAt,
    MessageType type
) {
    public ChatMessage {
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or blank");
        }
        if (type == null) {
            throw new IllegalArgumentException("Message type cannot be null");
        }
        if (sentAt == null) {
            sentAt = Instant.now();
        }
    }

    public static ChatMessage chat(UUID messageId, UUID roomId, UUID userId, String username, String content) {
        return new ChatMessage(messageId, roomId, userId, username, content, Instant.now(), MessageType.CHAT);
    }

    public static ChatMessage systemNotification(UUID roomId, String content) {
        return new ChatMessage(null, roomId, null, "System", content, Instant.now(), MessageType.SYSTEM);
    }

    public static ChatMessage userJoined(UUID roomId, String username) {
        String content = username + " has joined the room.";
        return new ChatMessage(null, roomId, null, "System", content, Instant.now(), MessageType.USER_JOINED);
    }

    public static ChatMessage userLeft(UUID roomId, String username) {
        String content = username + " has left the room.";
        return new ChatMessage(null, roomId, null, "System", content, Instant.now(), MessageType.USER_LEFT);
    }
}
