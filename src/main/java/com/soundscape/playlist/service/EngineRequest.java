package com.soundscape.playlist.service;

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
        private UserContext user_context;
        private List<Object> messages;
    }

    @Getter
    @AllArgsConstructor
    public static class UserContext {
        private String location;
        private String goal;
        private String decibel;
    }
}
