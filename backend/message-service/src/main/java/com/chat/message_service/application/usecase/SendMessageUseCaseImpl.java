package com.chat.message_service.application.usecase;

import java.util.UUID;

import com.chat.message_service.application.port.in.SendMessageUseCase;
import com.chat.message_service.application.port.out.EventPublisherPort;
import com.chat.message_service.application.port.out.MessagePersistencePort;
import com.chat.message_service.domain.event.MessageSentEvent;
import com.chat.message_service.domain.model.Message;

public class SendMessageUseCaseImpl implements SendMessageUseCase {

    private final MessagePersistencePort messagePersistencePort;
    private final EventPublisherPort eventPublisherPort;

    public SendMessageUseCaseImpl(MessagePersistencePort messagePersistencePort, EventPublisherPort eventPublisherPort) {
        this.messagePersistencePort = messagePersistencePort;
        this.eventPublisherPort = eventPublisherPort;
    }

    @Override
    public Message execute(UUID roomId, UUID userId, String username, String content) {
        Message message = Message.create(roomId, userId, username, content);
        Message savedMessage = messagePersistencePort.save(message);
        MessageSentEvent event = MessageSentEvent.of(
            savedMessage.getId().getValue(), 
            savedMessage.getRoomId(),
            savedMessage.getUserId(),
            savedMessage.getUsername(),
            savedMessage.getContentAsString()
        );

        eventPublisherPort.publishMessageSent(event);

        return savedMessage;
    }
    
}
