package com.chat.message_service.domain.model;

import java.time.Instant;
import java.util.UUID;

import com.chat.message_service.domain.valueobject.MessageContent;
import com.chat.message_service.domain.valueobject.MessageId;

public final class Message {
    private static final int MAX_CONTENT_LENGTH = 500;
    private static final int MIN_CONTENT_LENGTH = 1;

    private final MessageId id;
    private final UUID roomId;
    private final UUID userId;
    private final String username;
    private final MessageContent content;
    private final Instant sentAt;
    
    private Message(MessageId id, UUID roomId, UUID userId, String username, MessageContent content, Instant sentAt) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.sentAt = sentAt;
    }

    public MessageId getId() {
        return id;
    }
    public UUID getRoomId() {
        return roomId;
    }
    public UUID getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public MessageContent getContent() {
        return content;
    }
    public Instant getSentAt() {
        return sentAt;
    }

    public static Message create(
        UUID roomId,
        UUID userId,
        String username,
        String content
    ) {
        validateRoomId(roomId);
        validateUserId(userId);
        validateUsername(username);

        return new Message(
            MessageId.generate(),
            roomId,
            userId,
            username,
            MessageContent.of(content),
            Instant.now()
        );
    }

    public static Message reconstitute(
        MessageId id,
        UUID roomid,
        UUID userId,
        String username,
        MessageContent content,
        Instant sentAt
    ) {
        return new Message(
            id,
            roomid,
            userId,
            username,
            content,
            sentAt
        );
    }

    private static void validateRoomId(UUID roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
    }

    private static void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }

    private static void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() > 20) {
            throw new IllegalArgumentException("Username cannot exceed 20 characters");
        }
    }

    public String getContentAsString() {
        return this.content.getValue();
    }

}
