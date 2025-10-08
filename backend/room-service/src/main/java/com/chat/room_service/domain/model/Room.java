package com.chat.room_service.domain.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Room {
    public static final int MAX_USERS = 10;

    private final RoomId id;
    private final String name;
    private final List<RoomMember> members;
    private RoomStatus status;
    private final Instant createdAt;

    private Room(RoomId id, String name, List<RoomMember> members, RoomStatus status, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Room create(String name) {
        validateName(name);
        return new Room(
            RoomId.generate(),
            name,
            new ArrayList<>(),
            RoomStatus.OPEN,
            Instant.now()
        );
    }

    public static Room reconstitute(RoomId id, String name, List<RoomMember> members, RoomStatus status, Instant createdAt) {
        // Crear una nueva lista mutable a partir de la lista que viene de la DB
        return new Room(id, name, new ArrayList<>(members), status, createdAt);
    }

    public void addMember(UUID userId, String username) {
        if (status == RoomStatus.FULL) {
            throw new IllegalStateException("Room is full");
        }
        if (status == RoomStatus.CLOSED) {
            throw new IllegalStateException("Room is closed");
        }
        if (hasMember(userId)) {
            throw new IllegalArgumentException("User is already in the room");
        }
        if (members.size() >= MAX_USERS) {
            throw new IllegalStateException("Cannot add more members, room is full");
        }

        members.add(RoomMember.of(userId, username));
        updateStatus();
    }

    public void removeMember(UUID userId) {
        boolean removed = members.removeIf(member -> member.userId().equals(userId));
        if (!removed) {
            throw new IllegalArgumentException("User is not in the room");
        }
        updateStatus();
    }

    public boolean hasMember(UUID userId) {
        return members.stream()
            .anyMatch(member -> member.userId().equals(userId));
    }

    public boolean hasSpace() {
        return members.size() < MAX_USERS;
    }

    private void updateStatus() {
        int memberCount = members.size();

        if (memberCount == 0) {
            this.status = RoomStatus.CLOSED;
        } else if (memberCount >= MAX_USERS) {
            this.status = RoomStatus.FULL;
        } else {
            this.status = RoomStatus.OPEN;
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Room name cannot be null or blank");
        }
        if (name.length() < 3 || name.length() > 50) {
            throw new IllegalArgumentException("Room name must be between 3 and 50 characters");
        }
    }

    // Getters

    public RoomId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RoomMember> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public RoomStatus getStatus() {
        return status;
    }

    public int getCurrentUsers() {
        return members.size();
    }

    public int getMaxUsers() {
        return MAX_USERS;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public boolean isEmpty() {
        return members.isEmpty();
    }

    public boolean isFull() {
        return members.size() >= MAX_USERS;
    }

    public boolean isOpen() {
        return status == RoomStatus.OPEN;
    }
}
