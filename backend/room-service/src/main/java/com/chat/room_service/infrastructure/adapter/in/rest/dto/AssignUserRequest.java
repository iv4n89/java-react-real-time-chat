package com.chat.room_service.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

public record AssignUserRequest(
    UUID userId,
    String username
) {
    public AssignUserRequest {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username cannot be null or blank");
        }
    }
}
