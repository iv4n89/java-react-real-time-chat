package com.chat.websocket_gateway.application.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.chat.websocket_gateway.application.port.in.BroadcastMessageUseCase;
import com.chat.websocket_gateway.domain.model.ChatMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ChatMessageEventListener {
    private final BroadcastMessageUseCase broadcastMessageUseCase;
    private final ObjectMapper objectMapper;

    public ChatMessageEventListener(BroadcastMessageUseCase broadcastMessageUseCase, ObjectMapper objectMapper) {
        this.broadcastMessageUseCase = broadcastMessageUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "chat.messages", groupId = "websocket-gateway-group")
    public void consumeChatMessage(String message) {
        try {
            System.out.println("Received chat message from Kafka");

            JsonNode eventNode = objectMapper.readTree(message);

            UUID messageId = UUID.fromString(eventNode.get("messageId").asText());
            UUID roomId = UUID.fromString(eventNode.get("roomId").asText());
            UUID userId = UUID.fromString(eventNode.get("userId").asText());
            String username = eventNode.get("username").asText();
            String content = eventNode.get("content").asText();

            ChatMessage chatMessage = ChatMessage.chat(messageId, roomId, userId, username, content);

            broadcastMessageUseCase.execute(chatMessage);
        } catch (Exception e) {
            System.err.println("Error processing chat message event: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
