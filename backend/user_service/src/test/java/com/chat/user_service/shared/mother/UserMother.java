package com.chat.user_service.shared.mother;

import java.time.Instant;
import java.util.UUID;

import com.chat.user_service.domain.model.User;
import com.chat.user_service.domain.model.UserId;
import com.github.javafaker.Faker;

public class UserMother {
    private static final Faker faker = new Faker();

    public static User random() {
        return User.reconstitute(
            UserId.generate(), 
            faker.name().username(), 
            UUID.randomUUID(), 
            Instant.now(), 
            Instant.now()
        );
    }
}
