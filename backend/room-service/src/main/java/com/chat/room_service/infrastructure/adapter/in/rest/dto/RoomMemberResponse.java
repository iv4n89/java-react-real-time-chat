package com.chat.room_service.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

import com.chat.room_service.domain.model.RoomMember;

public record RoomMemberResponse(
    UUID userId,
    String username,
    String joinedAt
) {
    public static RoomMemberResponse fromDomain(RoomMember member) {
        return new RoomMemberResponse(
            member.userId(),
            member.username(),
            member.joinedAt().toString()
        );
    }
}
