package com.chat.websocket_gateway.application.port.out;

import java.util.UUID;

import com.chat.websocket_gateway.domain.model.ChatMessage;

public interface WebSocketMessagingPort {
    void sendToRoom(UUID roomId, ChatMessage message);
    void sendToUser(UUID userId, ChatMessage message);
}
