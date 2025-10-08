package com.chat.room_service.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic roomEventsTopic() {
        return new NewTopic("room.events", 3, (short) 1);
    }
}
