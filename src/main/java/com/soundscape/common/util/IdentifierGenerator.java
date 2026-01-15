package com.soundscape.common.util;

import org.apache.commons.text.RandomStringGenerator;

public class IdentifierGenerator {
    private static final int ID_LENGTH = 20;

    private static final RandomStringGenerator GENERATOR = new RandomStringGenerator.Builder()
            .withinRange('0', 'z')
            .filteredBy(Character::isLetterOrDigit)
            .get();

    public static String generateId(int length) {
        return GENERATOR.generate(length);
    }

    public static String generateWithPrefix(String prefix) {
        return prefix + generateId(ID_LENGTH - prefix.length());
    }
}