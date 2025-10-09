package com.chat.message_service.application.service;

import java.util.List;
import java.util.UUID;

import com.chat.message_service.application.port.in.GetMessageHistoryUseCase;
import com.chat.message_service.application.port.in.GetRecentMessagesUseCase;
import com.chat.message_service.application.port.in.SendMessageUseCase;
import com.chat.message_service.domain.model.Message;

public final class MessageApplicationService {
    private final SendMessageUseCase sendMessageUseCase;
    private final GetMessageHistoryUseCase getMessageHistoryUseCase;
    private final GetRecentMessagesUseCase getRecentMessagesUseCase;

    public MessageApplicationService(SendMessageUseCase sendMessageUseCase,
            GetMessageHistoryUseCase getMessageHistoryUseCase,
            GetRecentMessagesUseCase getRecentMessagesUseCase) {
        this.sendMessageUseCase = sendMessageUseCase;
        this.getMessageHistoryUseCase = getMessageHistoryUseCase;
        this.getRecentMessagesUseCase = getRecentMessagesUseCase;
    }

    public Message sendMessage(UUID roomId, UUID userId, String username, String content) {
        return sendMessageUseCase.execute(roomId, userId, username, content);
    }

    public List<Message> getMessageHistory(UUID roomId) {
        return getMessageHistoryUseCase.execute(roomId);
    }

    public List<Message> getRecentMessages(UUID roomId, int limit) {
        return getRecentMessagesUseCase.execute(roomId, limit);
    }
}
