package com.chat.websocket_gateway.infrastructure.adapter.out.memory;

import java.util.UUID;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.chat.websocket_gateway.application.port.out.WebSocketMessagingPort;
import com.chat.websocket_gateway.domain.model.ChatMessage;

@Component
public class WebSocketMessagingAdapter implements WebSocketMessagingPort {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketMessagingAdapter(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendToRoom(UUID roomId, ChatMessage message) {
        String destination = "/topic/room/" + roomId;
        messagingTemplate.convertAndSend(destination, message);
        System.out.println("Sent to " + destination + ": " + message.content());
    }

    @Override
    public void sendToUser(UUID userId, ChatMessage message) {
        String destination = "/queue/messages";
        messagingTemplate.convertAndSendToUser(userId.toString(), destination, message);
        System.out.println("Sent to user " + userId + ": " + message.content());
    }
}
