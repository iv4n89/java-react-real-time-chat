package com.chat.message_service.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.chat.message_service.application.port.out.MessagePersistencePort;
import com.chat.message_service.domain.model.Message;
import com.chat.message_service.domain.valueobject.MessageId;
import com.chat.message_service.infrastructure.mapper.MessageMapper;

@Component
public class MessageJpaAdapter implements MessagePersistencePort {
    private final MessageJpaRepository messageJpaRepository;

    public MessageJpaAdapter(MessageJpaRepository messageJpaRepository) {
        this.messageJpaRepository = messageJpaRepository;
    }

    @Override
    public Message save(Message message) {
        MessageEntity entity = MessageMapper.toEntity(message);
        MessageEntity savedEntity = messageJpaRepository.save(entity);
        return MessageMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Message> findById(MessageId id) {
        return messageJpaRepository.findById(id.getValue())
            .map(MessageMapper::toDomain);
    }

    @Override
    public List<Message> findByRoomId(UUID roomId) {
        return messageJpaRepository.findByRoomId(roomId).stream()
            .map(MessageMapper::toDomain)
            .toList();
    }

    @Override
    public List<Message> findByRoomIdOrderBySentAtDesc(UUID roomId) {
        return messageJpaRepository.findByRoomIdOrderBySentAtDesc(roomId).stream()
            .map(MessageMapper::toDomain)
            .toList();
    }

    @Override
    public List<Message> findTopNByRoomIdOrderBySentAtDesc(UUID roomId, int limit) {
        return messageJpaRepository.findTopNByRoomIdOrderBySentAtDesc(roomId, limit).stream()
            .map(MessageMapper::toDomain)
            .toList();
    }

    @Override
    public void deleteById(MessageId id) {
        messageJpaRepository.deleteById(id.getValue());
    }
}
