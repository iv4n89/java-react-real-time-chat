package com.chat.websocket_gateway.application.port.in;

import com.chat.websocket_gateway.domain.model.ChatMessage;

public interface BroadcastMessageUseCase {
    void execute(ChatMessage message);
}
