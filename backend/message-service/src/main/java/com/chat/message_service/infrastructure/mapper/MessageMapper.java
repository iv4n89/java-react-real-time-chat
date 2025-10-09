package com.chat.message_service.infrastructure.mapper;

import com.chat.message_service.domain.model.Message;
import com.chat.message_service.domain.valueobject.MessageContent;
import com.chat.message_service.domain.valueobject.MessageId;
import com.chat.message_service.infrastructure.adapter.out.persistence.MessageEntity;

public class MessageMapper {
    public static MessageEntity toEntity(Message message) {
        MessageEntity entity = new MessageEntity();
        entity.setId(message.getId().getValue());
        entity.setRoomId(message.getRoomId());
        entity.setUserId(message.getUserId());
        entity.setUsername(message.getUsername());
        entity.setContent(message.getContentAsString());
        entity.setSentAt(message.getSentAt());
        return entity;
    }

    public static Message toDomain(MessageEntity entity) {
        return Message.reconstitute(
            new MessageId(entity.getId()),
            entity.getRoomId(),
            entity.getUserId(),
            entity.getUsername(),
            new MessageContent(entity.getContent()),
            entity.getSentAt()
        );
    }
}
