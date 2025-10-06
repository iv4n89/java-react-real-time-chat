package com.chat.user_service.infrastructure.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chat.user_service.application.port.in.DisconnectUserUseCase;
import com.chat.user_service.application.port.in.GetUserUseCase;
import com.chat.user_service.application.port.in.JoinChatUseCase;
import com.chat.user_service.application.port.out.EventPublisherPort;
import com.chat.user_service.application.port.out.UserPersistencePort;
import com.chat.user_service.application.service.UserApplicationService;
import com.chat.user_service.domain.model.User;
import com.chat.user_service.domain.model.UserId;
import com.chat.user_service.domain.repository.UserRepository;
import com.chat.user_service.domain.service.UserDomainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class BeanConfiguration {
    @Bean
    public UserDomainService userDomainService(UserPersistencePort userPersistencePort) {
        return new UserDomainService(new UserRepository() {
            @Override
            public User save(User user) {
                return userPersistencePort.save(user);
            }

            @Override
            public Optional<User> findById(UserId userId) {
                return userPersistencePort.findById(userId);
            }

            @Override
            public Optional<User> findByUsername(String username) {
                return userPersistencePort.findByUsername(username);
            }

            @Override
            public boolean existsByUsername(String username) {
                return userPersistencePort.existsByUsername(username);
            }

            @Override
            public void deleteById(UserId userId) {
                userPersistencePort.deleteById(userId);
            }
        });
    }

    /**
     * Servicio de aplicación que implementa todos los casos de uso
     * Spring lo inyectará automáticamente en el controlador
     */
    @Bean
    public UserApplicationService userApplicationService(
        UserPersistencePort userPersistencePort,
        EventPublisherPort eventPublisherPort,
        UserDomainService userDomainService
    ) {
        return new UserApplicationService(userPersistencePort, eventPublisherPort, userDomainService);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
