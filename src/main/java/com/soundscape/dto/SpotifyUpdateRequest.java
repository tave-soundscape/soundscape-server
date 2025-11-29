package com.soundscape.dto;

import jakarta.validation.constraints.NotBlank;

public record SpotifyUpdateRequest(
        String authorizationCode,
        String redirectUri
) {}