package com.chat.message_service.application.usecase;

import java.util.List;
import java.util.UUID;

import com.chat.message_service.application.port.in.GetRecentMessagesUseCase;
import com.chat.message_service.application.port.out.MessagePersistencePort;
import com.chat.message_service.domain.model.Message;

public class GetRecentMessagesUseCaseImpl implements GetRecentMessagesUseCase {

    private final MessagePersistencePort messagePersistencePort;

    public GetRecentMessagesUseCaseImpl(MessagePersistencePort messagePersistencePort) {
        this.messagePersistencePort = messagePersistencePort;
    }

    @Override
    public List<Message> execute(UUID roomId, int limit) {
        if (limit <= 0) {
            limit = 50;
        }
        if (limit > 200) {
            limit = 200;
        }
        return messagePersistencePort.findTopNByRoomIdOrderBySentAtDesc(roomId, limit);
    }
    
}
