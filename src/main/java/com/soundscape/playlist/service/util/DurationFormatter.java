package com.soundscape.playlist.service.util;

public class DurationFormatter {

    public static String format(int durationMs) {
        int minutes = (durationMs / 1000) / 60;
        int seconds = (durationMs / 1000) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
