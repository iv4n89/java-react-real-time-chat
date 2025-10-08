package com.chat.room_service.infrastructure.adapter.out.messaging;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.chat.room_service.application.service.UserEventConsumerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaUserEventConsumer {
    private final UserEventConsumerService userEventConsumerService;
    private final ObjectMapper objectMapper;

    public KafkaUserEventConsumer(UserEventConsumerService userEventConsumerService, ObjectMapper objectMapper) {
        this.userEventConsumerService = userEventConsumerService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "user.events", groupId = "room-service-group")
    public void consumeUserEvent(String message,
                                 @Header(value = "kafka_receivedMessageKey", required = false) String eventType) {
        System.out.println("========================================");
        System.out.println("📨 RECEIVED KAFKA MESSAGE");
        System.out.println("Event Type: " + eventType);
        System.out.println("Message: " + message);
        System.out.println("========================================");

        try {
            JsonNode eventNode = objectMapper.readTree(message);
            UUID userId = UUID.fromString(eventNode.get("userId").asText());

            if (eventType == null || eventType.isBlank()) {
                System.err.println("⚠️  Event type is NULL or empty! Cannot process event.");
                return;
            }

            switch (eventType) {
                case "UserCreated" -> {
                    String username = eventNode.get("username").asText();
                    System.out.println("🔵 Processing UserCreated: " + username);
                    userEventConsumerService.handleUserCreated(userId, username);
                    System.out.println("✅ User created and assigned: " + username);
                }
                case "UserDisconnected" -> {
                    System.out.println("🔴 Processing UserDisconnected: " + userId);
                    userEventConsumerService.handleUserDisconnected(userId);
                    System.out.println("✅ User disconnected: " + userId);
                }
                default -> System.out.println("⚠️  Unknown event type: " + eventType);
            }
        } catch (Exception e) {
            System.err.println("❌ ERROR processing user event:");
            System.err.println("Message: " + message);
            System.err.println("Event Type: " + eventType);
            e.printStackTrace();
        }
    }
}
