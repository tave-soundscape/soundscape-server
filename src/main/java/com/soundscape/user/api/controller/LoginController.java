package com.soundscape.user.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.user.api.dto.LoginRequestDto;
import com.soundscape.user.api.dto.LoginResponseDto;
import com.soundscape.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController implements LoginControllerDoc {

    private final LoginService loginService;

    // TODO: 추후 삭제 - 카카오 로그인 테스트용 메서드
    @GetMapping
    public String getAuthorizationCode() {
        String authorizationUrl = loginService.getLoginPage();
        return authorizationUrl;
    }

    // TODO: 추후 삭제 - 카카오 로그인 테스트용 메서드
    @GetMapping("/kakao/callback")
    public String kakaoCallback(@RequestParam String code) {
        return "카카오에서 받은 인가 코드: " + code;
    }

    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginRequestDto requestDto) {
        String userCode = requestDto.getCode();
        LoginResponseDto loginResponse = loginService.login(userCode);
        return CommonResponse.success(loginResponse, "로그인에 성공했습니다.");
    }
}
