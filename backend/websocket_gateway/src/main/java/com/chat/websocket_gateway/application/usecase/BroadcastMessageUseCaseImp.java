package com.chat.websocket_gateway.application.usecase;

import com.chat.websocket_gateway.application.port.in.BroadcastMessageUseCase;
import com.chat.websocket_gateway.application.port.out.WebSocketMessagingPort;
import com.chat.websocket_gateway.domain.model.ChatMessage;

public class BroadcastMessageUseCaseImp implements BroadcastMessageUseCase {
    private final WebSocketMessagingPort webSocketMessagingPort;

    public BroadcastMessageUseCaseImp(WebSocketMessagingPort webSocketMessagingPort) {
        this.webSocketMessagingPort = webSocketMessagingPort;
    }

    @Override
    public void execute(ChatMessage message) {
        System.out.println("Broadcasting message to room: " + message.roomId());
        webSocketMessagingPort.sendToRoom(message.roomId(), message);
    }
}
