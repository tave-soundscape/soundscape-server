package com.soundscape.playlist.service.command;

import lombok.Getter;

@Getter
public class PlaylistCommand {

    private final String location;
    private final String decibel;
    private final String goal;

    public PlaylistCommand(String location, String decibel, String goal) {
        this.location = location;
        this.decibel = decibel;
        this.goal = goal;
    }
}
