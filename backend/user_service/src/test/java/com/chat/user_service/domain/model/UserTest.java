package com.chat.user_service.domain.model;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.chat.user_service.shared.mother.MotherWord;
import com.chat.user_service.shared.mother.UserMother;

public class UserTest {
    
    @Test
    void shouldCreateUserWithValidData() {
        // Given
        User user = UserMother.random();

        // Then
        assert user != null;
        assert user.getUsername() != null;
    }

    @Test
    void shouldThrowIfNonValidDataInUser() {
        // Given
        String nonValidId = "Non-valid";

        try {
            // When
            User.reconstitute(UserId.of(nonValidId), 
            MotherWord.random(7), 
            UUID.randomUUID(), 
            Instant.now(), 
            Instant.now());
        } catch (IllegalArgumentException e) {
            // Then
            assert e.getMessage().contains("Invalid UUID string: " + nonValidId);
        }
    }
}
