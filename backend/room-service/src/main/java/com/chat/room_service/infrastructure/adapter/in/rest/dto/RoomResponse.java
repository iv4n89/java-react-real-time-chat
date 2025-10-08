package com.chat.room_service.infrastructure.adapter.in.rest.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.chat.room_service.domain.model.Room;
import com.chat.room_service.domain.model.RoomStatus;

public record RoomResponse(
    UUID roomId,
    String name,
    int currentUsers,
    int maxUsers,
    RoomStatus status,
    List<RoomMemberResponse> members,
    String createdAt
) {
    public static RoomResponse fromDomain(Room room) {
        List<RoomMemberResponse> memberResponses = room.getMembers().stream()
            .map(RoomMemberResponse::fromDomain)
            .collect(Collectors.toList());

        return new RoomResponse(
            room.getId().value(),
            room.getName(),
            room.getCurrentUsers(),
            room.getMaxUsers(),
            room.getStatus(),
            memberResponses,
            room.getCreatedAt().toString()
        );
    }
}
