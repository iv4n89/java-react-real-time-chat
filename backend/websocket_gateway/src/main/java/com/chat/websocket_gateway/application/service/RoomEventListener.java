package com.chat.websocket_gateway.application.service;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.chat.websocket_gateway.application.port.in.BroadcastMessageUseCase;
import com.chat.websocket_gateway.domain.model.ChatMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RoomEventListener {
    private final BroadcastMessageUseCase broadcastMessageUseCase;
    private final ObjectMapper objectMapper;

    public RoomEventListener(BroadcastMessageUseCase broadcastMessageUseCase, ObjectMapper objectMapper) {
        this.broadcastMessageUseCase = broadcastMessageUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "room.events", groupId = "websocket-gateway-group")
    public void consumeRoomEvent(String message, @Header(value = "kafka_receivedMessageKey", required = false) String eventType) {
        try {
            System.out.println("Received room event type: " + eventType);
            System.out.println("Received room event message: " + message);

            JsonNode eventNode = objectMapper.readTree(message);

            if (eventType == null) {
                return;
            }

            switch (eventType) {
                case "UserJoinedRoom" -> handleUserJoinedRoom(eventNode);
                case "UserLeftRoom" -> handleUserLeftRoom(eventNode);
                case "RoomFull" -> handleRoomFull(eventNode);
                case "RoomClosed" -> handleRoomClosed(eventNode);
                default -> System.out.println("Unknown event type: " + eventType);
            }
        } catch (Exception e) {
            System.err.println("Error processing room event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleUserJoinedRoom(JsonNode event) {
        UUID roomId = UUID.fromString(event.get("roomId").asText());
        String username = event.get("username").asText();
        int currentUsers = event.get("currentUsers").asInt();

        ChatMessage notification = ChatMessage.systemNotification(roomId, username + " has joined the room (" + currentUsers + " users now)");
        broadcastMessageUseCase.execute(notification);
    }

    private void handleUserLeftRoom(JsonNode event) {
        UUID roomId = UUID.fromString(event.get("roomId").asText());
        String username = event.get("username").asText();
        int remainingUsers = event.get("remainingUsers").asInt();

        ChatMessage notification = ChatMessage.systemNotification(roomId, username + " has left the room (" + remainingUsers + " users remaining)");
        broadcastMessageUseCase.execute(notification);
    }

    private void handleRoomFull(JsonNode event) {
        UUID roomId = UUID.fromString(event.get("roomId").asText());
        String roomName = event.get("roomName").asText();

        ChatMessage notification = ChatMessage.systemNotification(roomId, "The room '" + roomName + "' is now full.");
        broadcastMessageUseCase.execute(notification);
    }

    private void handleRoomClosed(JsonNode event) {
        UUID roomId = UUID.fromString(event.get("roomId").asText());
        String roomName = event.get("roomName").asText();

        ChatMessage notification = ChatMessage.systemNotification(roomId, "The room '" + roomName + "' has been closed.");
        broadcastMessageUseCase.execute(notification);
    }
}
