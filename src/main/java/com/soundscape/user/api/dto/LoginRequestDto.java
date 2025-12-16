package com.soundscape.user.api.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private final String code;

    public LoginRequestDto(String code) {
        this.code = code;
    }
}