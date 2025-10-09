package com.chat.message_service.infrastructure.adapter.out.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.chat.message_service.application.port.out.EventPublisherPort;
import com.chat.message_service.domain.event.MessageSentEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaEventPublisher implements EventPublisherPort {
    private static final String CHAT_MESSAGES_TOPIC = "chat.messages";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishMessageSent(MessageSentEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(CHAT_MESSAGES_TOPIC, event.roomId().toString(), eventJson);
            System.err.println("Published event to Kafka: " + eventJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize MessageSentEvent", e);
        }
    }
}
