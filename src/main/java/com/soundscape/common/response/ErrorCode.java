package com.soundscape.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다."),

    // USER
    USER_NOT_FOUND("존재하지 않는 회원입니다: %s"),

    // spotify API 에러
    SPOTIFY_API_ERROR("Spotify API 요청 중 오류가 발생했습니다"),

    //JWT
    JWT_NOT_FOUND("헤더에 인증 토큰이 존재하지 않습니다."),
    JWT_INVALID_TOKEN("유효하지 않은 토큰입니다."),
    JWT_EXPIRED_TOKEN("만료된 토큰입니다."),
    JWT_ERROR("토큰 처리 중 오류가 발생했습니다.");

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
