package com.chat.message_service.infrastructure.config;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chat.message_service.application.port.in.GetMessageHistoryUseCase;
import com.chat.message_service.application.port.in.GetRecentMessagesUseCase;
import com.chat.message_service.application.port.in.SendMessageUseCase;
import com.chat.message_service.application.port.out.EventPublisherPort;
import com.chat.message_service.application.port.out.MessagePersistencePort;
import com.chat.message_service.application.service.MessageApplicationService;
import com.chat.message_service.application.usecase.GetMessageHistoryUseCaseImpl;
import com.chat.message_service.application.usecase.GetRecentMessagesUseCaseImpl;
import com.chat.message_service.application.usecase.SendMessageUseCaseImpl;
import com.chat.message_service.domain.model.Message;
import com.chat.message_service.domain.repository.MessageRepository;
import com.chat.message_service.domain.service.MessageDomainService;
import com.chat.message_service.domain.valueobject.MessageId;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class BeanConfiguration {
    
    @Bean
    public SendMessageUseCase sendMessageUseCase(
        MessagePersistencePort messagePersistencePort,
        EventPublisherPort eventPublisherPort
    ) {
        return new SendMessageUseCaseImpl(messagePersistencePort, eventPublisherPort);
    }

    @Bean
    public GetMessageHistoryUseCase getMessageHistoryUseCase(
        MessagePersistencePort messagePersistencePort
    ) {
        return new GetMessageHistoryUseCaseImpl(messagePersistencePort);
    }

    @Bean
    public GetRecentMessagesUseCase getRecentMessagesUseCase(
        MessagePersistencePort messagePersistencePort
    ) {
        return new GetRecentMessagesUseCaseImpl(messagePersistencePort);
    }

    @Bean
    public MessageApplicationService messageApplicationService(
        SendMessageUseCase sendMessageUseCase,
        GetMessageHistoryUseCase getMessageHistoryUseCase,
        GetRecentMessagesUseCase getRecentMessagesUseCase
    ) {
        return new MessageApplicationService(sendMessageUseCase, getMessageHistoryUseCase, getRecentMessagesUseCase);
    }

    @Bean
    public MessageDomainService messageDomainService(MessagePersistencePort messagePersistencePort) {
        return new MessageDomainService(new MessageRepository() {
            @Override
            public Message save(Message message) {
                return messagePersistencePort.save(message);
            }

            @Override
            public Optional<Message> findById(MessageId id) {
                return messagePersistencePort.findById(id);
            }

            @Override
            public List<Message> findByRoomId(UUID roomId) {
                return messagePersistencePort.findByRoomId(roomId);
            }

            @Override
            public List<Message> findByRoomIdOrderBySentAtDesc(UUID roomId) {
                return messagePersistencePort.findByRoomIdOrderBySentAtDesc(roomId);
            }

            @Override
            public List<Message> findTopNByRoomIdOrderBySentAtDesc(UUID roomId, int limit) {
                return messagePersistencePort.findTopNByRoomIdOrderBySentAtDesc(roomId, limit);
            }

            @Override
            public void deleteById(MessageId id) {
                messagePersistencePort.deleteById(id);
            }
        });
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
