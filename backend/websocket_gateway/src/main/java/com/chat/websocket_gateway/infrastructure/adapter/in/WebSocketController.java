package com.chat.websocket_gateway.infrastructure.adapter.in;

import java.util.UUID;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.chat.websocket_gateway.application.port.in.BroadcastMessageUseCase;
import com.chat.websocket_gateway.domain.model.ChatMessage;

@Controller
public class WebSocketController {
    private final BroadcastMessageUseCase broadcastMessageUseCase;

    public WebSocketController(BroadcastMessageUseCase broadcastMessageUseCase) {
        this.broadcastMessageUseCase = broadcastMessageUseCase;
    }

    public void handleChatMessage(
        @DestinationVariable UUID roomId,
        @Payload ChatMessageRequest request
    ) {
        System.out.println("Received WebSocket message from client: " + request.username());

        ChatMessage message = ChatMessage.chat(UUID.randomUUID(), roomId, request.userId(), request.username(), request.content());

        broadcastMessageUseCase.execute(message);
    }

    private record ChatMessageRequest(
        UUID userId,
        String username,
        String content
    ) {}
}
