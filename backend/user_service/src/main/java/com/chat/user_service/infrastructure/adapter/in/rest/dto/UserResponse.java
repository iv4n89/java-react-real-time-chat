package com.chat.user_service.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

import com.chat.user_service.domain.model.User;

public record UserResponse(
    UUID userId,
    String username,
    UUID roomId,
    String createdAt,
    String lastSeen
) {
    public static UserResponse fromDomain(User user) {
        return new UserResponse(
            user.getId().value(), 
            user.getUsername(), 
            user.getRoomId(), 
            user.getCreatedAt().toString(), 
            user.getLastSeen().toString()
        );
    }
}
