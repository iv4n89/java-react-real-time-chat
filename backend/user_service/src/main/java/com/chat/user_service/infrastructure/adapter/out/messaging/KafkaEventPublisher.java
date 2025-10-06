package com.chat.user_service.infrastructure.adapter.out.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.chat.user_service.application.port.out.EventPublisherPort;
import com.chat.user_service.domain.event.UserCreatedEvent;
import com.chat.user_service.domain.event.UserDisconnectedEvent;
import com.chat.user_service.domain.event.UserJoinedRoomEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaEventPublisher implements EventPublisherPort {
    private static final String USER_EVENTS_TOPIC = "user.events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishUserCreated(UserCreatedEvent event) {
        publishEvent(event, "UserCreated");
    }

    @Override
    public void publishUserJoinedRoom(UserJoinedRoomEvent event) {
        publishEvent(event, "UserJoinRoom");
    }

    @Override
    public void publishUserDisconnected(UserDisconnectedEvent event) {
        publishEvent(event, "UserDisconnected");
    }

    private void publishEvent(Object event, String eventType) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(USER_EVENTS_TOPIC, eventType, eventJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event: " + eventType, e);
        }
    }
}
