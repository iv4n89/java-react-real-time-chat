package com.chat.websocket_gateway.application.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserEventListener {
    private final ObjectMapper objectMapper;

    public UserEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "user.events", groupId = "websocket-gateway-group")
    public void consumeUserEvent(String message, @Header(value = "kafka_receivedMessageKey", required = false) String eventType) {
        try {
            System.out.println("Received user event: " + eventType);
        } catch (Exception e) {
            System.err.println("Error processing user event: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
