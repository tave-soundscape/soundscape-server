package com.soundscape.user.api.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private final String kakaoAccessToken;

    public LoginRequestDto(String kakaoAccessToken) {
        this.kakaoAccessToken = kakaoAccessToken;
    }
}