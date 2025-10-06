package com.chat.user_service.infrastructure.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.chat.user_service.application.port.out.UserPersistencePort;
import com.chat.user_service.domain.model.User;
import com.chat.user_service.domain.model.UserId;

@Component
public class UserJpaAdapter implements UserPersistencePort {
    private final UserJpaRepository userJpaRepository;

    public UserJpaAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(UserId userId) {
        return userJpaRepository.findById(userId.value())
            .map(this::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
            .map(this::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public void deleteById(UserId userId) {
        userJpaRepository.deleteById(userId.value());
    }

    // Mappers

    private UserEntity toEntity(User user) {
        return new UserEntity(
            user.getId().value(),
            user.getUsername(),
            user.getRoomId(),
            user.getCreatedAt(),
            user.getLastSeen()
        );
    }

    private User toDomain(UserEntity entity) {
        return User.reconstitute(
            new UserId(entity.getId()), 
            entity.getUsername(), 
            entity.getRoomId(), 
            entity.getCreatedAt(), 
            entity.getLastSeen()
        );
    }
}
