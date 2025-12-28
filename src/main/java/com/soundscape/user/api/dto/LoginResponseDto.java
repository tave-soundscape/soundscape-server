package com.soundscape.user.api.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final String accessToken;
    private final String refreshToken;
    private final Boolean isOnboarded;

    public LoginResponseDto(String accessToken, String refreshToken, Boolean isOnboarded) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isOnboarded = isOnboarded;
    }
}
