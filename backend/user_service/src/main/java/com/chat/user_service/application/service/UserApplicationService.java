package com.chat.user_service.application.service;

import java.util.UUID;

import com.chat.user_service.application.port.in.DisconnectUserUseCase;
import com.chat.user_service.application.port.in.GetUserUseCase;
import com.chat.user_service.application.port.in.JoinChatUseCase;
import com.chat.user_service.application.port.out.EventPublisherPort;
import com.chat.user_service.application.port.out.UserPersistencePort;
import com.chat.user_service.domain.event.UserCreatedEvent;
import com.chat.user_service.domain.event.UserDisconnectedEvent;
import com.chat.user_service.domain.model.User;
import com.chat.user_service.domain.model.UserId;
import com.chat.user_service.domain.service.UserDomainService;
import com.chat.user_service.shared.exception.UserNotFoundException;
import com.chat.user_service.shared.util.UsernameGenerator;

public class UserApplicationService implements JoinChatUseCase, GetUserUseCase, DisconnectUserUseCase {
    private final UserPersistencePort userPersistencePort;
    private final EventPublisherPort eventPublisherPort;
    private final UserDomainService userDomainService;

    public UserApplicationService(
        UserPersistencePort userPersistencePort,
        EventPublisherPort eventPublisherPort,
        UserDomainService userDomainService
    ) {
        this.userPersistencePort = userPersistencePort;
        this.eventPublisherPort = eventPublisherPort;
        this.userDomainService = userDomainService;
    }

    @Override
    public User execute() {
        // 1 Generate random unique username
        String baseUsername = UsernameGenerator.generative();
        String uniqueUsername = userDomainService.generateUniqueUsername(baseUsername);

        // 2 Create user
        User user = User.create(uniqueUsername);

        // 3 Persist user
        User savedUser = userPersistencePort.save(user);

        // 4 Publish domain event
        UserCreatedEvent event = UserCreatedEvent.of(
            savedUser.getId().value(), 
            savedUser.getUsername()
        );
        eventPublisherPort.publishUserCreated(event);

        return savedUser;
    }

    @Override
    public User execute(UUID userId) {
        UserId id = new UserId(userId);
        return userPersistencePort.findById(id)
            .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public void executeDisconnect(UUID userId) {
        // 1 Find user
        UserId id = new UserId(userId);
        User user = userPersistencePort.findById(id)
            .orElseThrow(() -> new UserNotFoundException(userId));

        // 2 Publish disconnect event
        UserDisconnectedEvent event = UserDisconnectedEvent.of(
            user.getId().value(), 
            user.getUsername(), 
            user.getRoomId()
        );
        eventPublisherPort.publishUserDisconnected(event);

        // 3 Delete user
        userPersistencePort.deleteById(id);
    }
}
