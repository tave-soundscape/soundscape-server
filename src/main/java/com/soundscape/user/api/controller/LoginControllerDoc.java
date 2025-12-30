package com.soundscape.user.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.user.api.dto.LoginRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Login", description = "로그인 API")
public interface LoginControllerDoc {

    @Operation(summary = "카카오 로그인 페이지 주소 조회(서버 테스트용)", description = "카카오 로그인 주소를 받아 카카오 엑세스토큰을 생성합니다. 추후 삭제 예정입니다.")
    String getLoginPage();
    @Operation(summary = "로그인", description = "사용장 앱에서 카카오 엑세스토큰을 받아 서버에서 로그인 처리 후 인증 토큰을 발급합니다.")
    CommonResponse login(@RequestBody LoginRequestDto requestDto);
}
