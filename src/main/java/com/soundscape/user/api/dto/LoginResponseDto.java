package com.soundscape.user.api.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final String accessToken;
    private final String refreshToken;
    //TODO: 추후 accessToken, refreshToken 외에 자체 JWT 토큰도 추가

    public LoginResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
