package com.soundscape.playlist.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistCondition {

    private String location;
    private String decibel;
    private String goal;

    public PlaylistCondition(String location, String decibel, String goal) {
        this.location = location;
        this.decibel = decibel;
        this.goal = goal;
    }
}
