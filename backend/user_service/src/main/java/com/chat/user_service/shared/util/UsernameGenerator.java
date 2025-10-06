package com.chat.user_service.shared.util;

import java.util.Random;

public class UsernameGenerator {
    private static final String[] ADJECTIVES = {
        "Happy", "Brave", "Swift", "Clever", "Silent",
        "Bright", "Cool", "Epic", "Fast", "Grand",
        "Lucky", "Mighty", "Noble", "Quick", "Smart",
        "Wise", "Bold", "Calm", "Eager", "Fierce",
        "Jolly", "Kind", "Proud", "Wild", "Zesty"
    };
    
    private static final String[] NOUNS = {
        "Tiger", "Eagle", "Wolf", "Bear", "Fox",
        "Lion", "Hawk", "Panda", "Dragon", "Phoenix",
        "Shark", "Falcon", "Cobra", "Panther", "Raven",
        "Leopard", "Jaguar", "Cheetah", "Viper", "Owl",
        "Lynx", "Badger", "Otter", "Beaver", "Raccoon"
    };
    
    private static final Random RANDOM = new Random();

    public static String generative() {
        String adjetive = ADJECTIVES[RANDOM.nextInt(NOUNS.length)];
        String noun = NOUNS[RANDOM.nextInt(NOUNS.length)];
        return adjetive + noun;
    }

    public static String generativeWithNumber() {
        int number = RANDOM.nextInt(100);
        return generative() + number;
    }
}
