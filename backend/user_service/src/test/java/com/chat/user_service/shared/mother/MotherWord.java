package com.chat.user_service.shared.mother;

import com.github.javafaker.Faker;

public class MotherWord {
    private static final Faker faker = new Faker();

    public static String random(int characters) {
        return faker.lorem().characters(characters);
    }
}
