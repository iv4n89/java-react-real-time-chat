package com.chat.user_service.domain.model;

import java.time.Instant;
import java.util.UUID;

public class User {
    private final UserId id;
    private final String username;
    private UUID roomId;
    private final Instant createdAt;
    private Instant lastSeen;

    private User(UserId id, String username, UUID roomId, Instant createdAt, Instant lastSeen) {
        this.id = id;
        this.username = username;
        this.roomId = roomId;
        this.createdAt = createdAt;
        this.lastSeen = lastSeen;
    }

    public static User create(String username) {
        validateUsername(username);
        return new User(
            UserId.generate(), 
            username, 
            null, 
            Instant.now(), 
            Instant.now()
            );
    }

    public static User reconstitute(UserId id, String username, UUID roomId, Instant createdAt, Instant lastSeen) {
        return new User(id, username, roomId, createdAt, lastSeen);
    }

    public void joinRoom(UUID roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException("RoomId cannot be null");
        }
        this.roomId = roomId;
        updateLastSeen();
    }

    public void leaveRoom() {
        this.roomId = null;
        updateLastSeen();
    }

    public void updateLastSeen() {
        this.lastSeen = Instant.now();
    }

    private static void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() < 3 || username.length() > 20) {
            throw new IllegalArgumentException("Username must be between 3 and 20 characters");
        }
    }

    // Getters
    public UserId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public boolean isInRoom() {
        return roomId != null;
    }
}
