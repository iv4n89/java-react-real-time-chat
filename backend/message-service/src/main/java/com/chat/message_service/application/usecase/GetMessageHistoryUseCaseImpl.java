package com.chat.message_service.application.usecase;

import java.util.List;
import java.util.UUID;

import com.chat.message_service.application.port.in.GetMessageHistoryUseCase;
import com.chat.message_service.application.port.out.MessagePersistencePort;
import com.chat.message_service.domain.model.Message;

public class GetMessageHistoryUseCaseImpl implements GetMessageHistoryUseCase {

    private final MessagePersistencePort messagePersistencePort;

    public GetMessageHistoryUseCaseImpl(MessagePersistencePort messagePersistencePort) {
        this.messagePersistencePort = messagePersistencePort;
    }

    @Override
    public List<Message> execute(UUID roomId) {
        return messagePersistencePort.findByRoomId(roomId);
    }
    
}
