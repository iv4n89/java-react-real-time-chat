package com.chat.room_service.infrastructure.adapter.out.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.chat.room_service.application.port.out.EventPublisherPort;
import com.chat.room_service.domain.event.RoomClosedEvent;
import com.chat.room_service.domain.event.RoomCreatedEvent;
import com.chat.room_service.domain.event.RoomFullEvent;
import com.chat.room_service.domain.event.UserJoinedRoomEvent;
import com.chat.room_service.domain.event.UserLeftRoomEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaEventPublisher implements EventPublisherPort {
    private static final String ROOM_EVENTS_TOPIC = "room.events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishRoomCreated(RoomCreatedEvent event) {
        publishEvent(event, "RoomCreated");
    }

    @Override
    public void publishUserJoinedRoom(UserJoinedRoomEvent event) {
        publishEvent(event, "UserJoinedRoom");
    }

    @Override
    public void publishUserLeftRoom(UserLeftRoomEvent event) {
        publishEvent(event, "UserLeftRoom");
    }

    @Override
    public void publishRoomFull(RoomFullEvent event) {
        publishEvent(event, "RoomFull");
    }

    @Override
    public void publishRoomClosed(RoomClosedEvent event) {
        publishEvent(event, "RoomClosed");
    }

    private void publishEvent(Object event, String eventType) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(ROOM_EVENTS_TOPIC, eventType, eventJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event: " + eventType, e);
        }
    }
}
