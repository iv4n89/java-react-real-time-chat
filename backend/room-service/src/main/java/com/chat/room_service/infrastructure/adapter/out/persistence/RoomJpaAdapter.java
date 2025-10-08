package com.chat.room_service.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.chat.room_service.application.port.out.RoomPersistencePort;
import com.chat.room_service.domain.model.Room;
import com.chat.room_service.domain.model.RoomId;
import com.chat.room_service.domain.model.RoomMember;
import com.chat.room_service.domain.model.RoomStatus;

@Component
public class RoomJpaAdapter implements RoomPersistencePort{
    private final RoomJpaRepository roomJpaRepository;

    public RoomJpaAdapter(RoomJpaRepository roomJpaRepository) {
        this.roomJpaRepository = roomJpaRepository;
    }

    @Override
    public Room save(Room room) {
        RoomEntity roomEntity = toEntity(room);
        RoomEntity savedEntity = roomJpaRepository.save(roomEntity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Room> findById(RoomId roomId) {
        return roomJpaRepository.findById(roomId.value())
            .map(this::toDomain);
    }

    @Override
    public Optional<Room> findByName(String name) {
        return roomJpaRepository.findByName(name)
            .map(this::toDomain);
    }

    @Override
    public List<Room> findByStatus(RoomStatus status) {
        return roomJpaRepository.findByStatus(status).stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public Optional<Room> findFirstByStatusOrderByCreatedAtAsc(RoomStatus status) {
        return roomJpaRepository.findFirstByStatusOrderByCreatedAtAsc(status)
            .map(this::toDomain);
    }

    @Override
    public Optional<Room> findByMemberUserId(UUID userId) {
        return roomJpaRepository.findByMemberUserId(userId)
            .map(this::toDomain);
    }

    @Override
    public List<Room> findAll() {
        return roomJpaRepository.findAll().stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public void deleteById(RoomId roomId) {
        roomJpaRepository.deleteById(roomId.value());
    }

    @Override
    public boolean existsByName(String name) {
        return roomJpaRepository.existsByName(name);
    }

    private RoomEntity toEntity(Room room) {
        RoomEntity entity = new RoomEntity();
        entity.setId(room.getId().value());
        entity.setName(room.getName());
        entity.setCurrentUsers(room.getCurrentUsers());
        entity.setMaxUsers(room.getMaxUsers());
        entity.setStatus(room.getStatus());
        entity.setCreatedAt(room.getCreatedAt());

        List<RoomMemberEntity> memberEntities = room.getMembers().stream()
            .map(member -> new RoomMemberEntity(
                entity,
                member.userId(),
                member.username(),
                member.joinedAt()
            ))
            .toList();

        entity.setMembers(memberEntities);

        return entity;
    }

    private Room toDomain(RoomEntity entity) {
        List<RoomMember> members = entity.getMembers().stream()
            .map(memberEntity -> new RoomMember(
                memberEntity.getUserId(),
                memberEntity.getUsername(),
                memberEntity.getJoinedAt()
            ))
            .toList();

        return Room.reconstitute(
            new RoomId(entity.getId()), 
            entity.getName(),
            members,
            entity.getStatus(),
            entity.getCreatedAt()
        );
    }
}
