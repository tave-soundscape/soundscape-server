package com.soundscape.playlist.service.util;

import java.security.InvalidParameterException;

public final class DecibelClassifier {

    private static final int QUIET_THRESHOLD = 50;
    private static final int MODERATE_THRESHOLD = 70;

    private static final String QUIET = "quiet";
    private static final String MODERATE = "moderate";
    private static final String LOUD = "loud";

    public static String categorize(Float decibel) {
        if (decibel == null) {
            throw new InvalidParameterException("데시벨 값은 null일 수 없습니다.");
        }

        if (decibel <= QUIET_THRESHOLD) {
            return QUIET;
        } else if (decibel <= MODERATE_THRESHOLD) {
            return MODERATE;
        } else {
            return LOUD;
        }
    }
}

