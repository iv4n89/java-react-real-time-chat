package com.chat.message_service.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageJpaRepository extends JpaRepository<MessageEntity, UUID> {
    List<MessageEntity> findByRoomId(UUID roomId);
    List<MessageEntity> findByRoomIdOrderBySentAtDesc(UUID roomId);
    @Query("SELECT m FROM MessageEntity m WHERE m.roomId = :roomId ORDER BY m.sentAt DESC LIMIT :limit")
    List<MessageEntity> findTopNByRoomIdOrderBySentAtDesc(@Param("roomId") UUID roomId, @Param("limit") int limit);
}
