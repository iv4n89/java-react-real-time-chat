package com.chat.user_service.domain.repository;

import java.util.Optional;

import com.chat.user_service.domain.model.User;
import com.chat.user_service.domain.model.UserId;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId userId);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    void deleteById(UserId userId);
}
