package com.chat.user_service.domain.service;

import com.chat.user_service.domain.model.User;
import com.chat.user_service.domain.repository.UserRepository;

public class UserDomainService {
    private final UserRepository userRepository;

    public UserDomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    public String generateUniqueUsername(String baseUsername) {
        if (isUsernameAvailable(baseUsername)) {
            return baseUsername;
        }

        int suffix = 1;
        String candidateUsername;
        do {
            candidateUsername = baseUsername + suffix;
            suffix++;
        } while (!isUsernameAvailable(candidateUsername) && suffix < 1000);

        if (suffix >= 1000) {
            throw new IllegalStateException("Could not generate unique username after 1000 attempts");
        }

        return candidateUsername;
    }

    public void validateCanJoinRoom(User user) {
        if (user.isInRoom()) {
            throw new IllegalStateException("User is already in a room");
        }
    }
}
