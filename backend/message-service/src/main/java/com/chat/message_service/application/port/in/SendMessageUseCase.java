package com.chat.message_service.application.port.in;

import java.util.UUID;

import com.chat.message_service.domain.model.Message;

public interface SendMessageUseCase {
    Message execute(UUID roomId, UUID userId, String username, String content);
}
