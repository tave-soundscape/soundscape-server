package com.soundscape.playlist.infra.engine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EngineRequest {
    private Input input;

    @Getter
    @AllArgsConstructor
    public static class Input {
        @JsonProperty("user_context")
        private UserContext userContext;
        private List<Object> messages;
    }

    @Getter
    @AllArgsConstructor
    public static class UserContext {
        private String location;
        private String goal;
        private String decibel;
        @JsonProperty("preferred_artists")
        private final List<String> fav_artists;
    }
}
