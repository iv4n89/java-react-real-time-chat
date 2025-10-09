package com.chat.websocket_gateway.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chat.websocket_gateway.application.port.in.BroadcastMessageUseCase;
import com.chat.websocket_gateway.application.port.in.ManageConnectionUseCase;
import com.chat.websocket_gateway.application.port.out.WebSocketMessagingPort;
import com.chat.websocket_gateway.application.service.ConnectionApplicationService;
import com.chat.websocket_gateway.application.usecase.BroadcastMessageUseCaseImp;
import com.chat.websocket_gateway.application.usecase.ManageConnectionUseCaseImpl;
import com.chat.websocket_gateway.domain.repository.ConnectionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class BeanConfiguration {
    
    @Bean
    public BroadcastMessageUseCase broadcastMessageUseCase(WebSocketMessagingPort webSocketMessagingPort) {
        return new BroadcastMessageUseCaseImp(webSocketMessagingPort);
    }

    @Bean
    public ManageConnectionUseCase manageConnectionUseCase(ConnectionRepository connectionRepository, BroadcastMessageUseCase broadcastMessageUseCase) {
        return new ManageConnectionUseCaseImpl(connectionRepository, broadcastMessageUseCase);
    }

    @Bean
    public ConnectionApplicationService connectionApplicationService(ManageConnectionUseCase manageConnectionUseCase) {
        return new ConnectionApplicationService(manageConnectionUseCase);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
