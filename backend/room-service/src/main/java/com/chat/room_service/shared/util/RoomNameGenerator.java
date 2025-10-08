package com.chat.room_service.shared.util;

import java.util.Random;

public class RoomNameGenerator {
    private static final String[] ADJECTIVES = {
        "Cosmic", "Electric", "Neon", "Crystal", "Shadow",
        "Quantum", "Mystic", "Thunder", "Solar", "Lunar",
        "Fire", "Ice", "Storm", "Wind", "Ocean",
        "Mountain", "Forest", "Desert", "Arctic", "Tropical",
        "Ancient", "Future", "Digital", "Cyber", "Retro"
    };
    
    private static final String[] NOUNS = {
        "Arena", "Lounge", "Hub", "Zone", "Realm",
        "Sanctuary", "Chamber", "Haven", "Nexus", "Gateway",
        "Palace", "Temple", "Fortress", "Castle", "Dome",
        "Plaza", "Square", "Garden", "Park", "Bay",
        "Valley", "Peak", "Oasis", "Island", "Cave"
    };

    private static final Random RANDOM = new Random();

    public static String generate() {
        String adjetive = ADJECTIVES[RANDOM.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[RANDOM.nextInt(NOUNS.length)];
        return adjetive + noun;
    }

    public static String generateWithNumber() {
        int number = RANDOM.nextInt(100);
        return generate() + number;
    }
}
