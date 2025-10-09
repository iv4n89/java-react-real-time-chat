package com.chat.message_service.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.chat.message_service.domain.model.Message;
import com.chat.message_service.domain.valueobject.MessageId;

public interface MessageRepository {
    Message save(Message message);
    Optional<Message> findById(MessageId id);
    List<Message> findByRoomId(UUID roomId);
    List<Message> findByRoomIdOrderBySentAtDesc(UUID roomId);
    List<Message> findTopNByRoomIdOrderBySentAtDesc(UUID roomId, int limit);
    void deleteById(MessageId id);
}
