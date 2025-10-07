package com.chat.user_service.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.chat.user_service.shared.mother.UserIdMother;

public class UserIdTest {
    
    @Test
    void shouldCreateUserIdWithValidData() {
        // Given
        UUID uuid = UUID.randomUUID();
        UserId userId = UserIdMother.of(uuid.toString());

        // Then
        assertEquals(uuid, userId.value());
    }

    @Test
    void shouldThrowIfNoValidDataInUserId() {
        // Given
        String nonValidUuid = "Non-valid";

        try {
            // When
            UserIdMother.of(nonValidUuid);
            // Then
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid UUID string: " + nonValidUuid, e.getMessage());
        }
    }
}
