package com.chat.message_service.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic chatMessagesTopic() {
        return new NewTopic("chat.message", 3, (short) 1);
    }
}
