package com.chat.user_service.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    /**
     * Crear topic 'user.events' automáticamente si no existe
     * 3 particiones para escalabilidad
     * Factor de replicación 1 (para desarrollo)
     *
     * Spring Boot autoconfigura KafkaTemplate y ProducerFactory
     * usando las propiedades en application.properties
     */
    @Bean
    public NewTopic userEventsTopic() {
        return new NewTopic("user.events", 3, (short) 1);
    }
}
