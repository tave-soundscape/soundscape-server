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
    public String getLoginPage() {
        return loginService.getLoginPage();
    }

    // TODO: 추후 삭제 - 카카오 로그인 테스트용 메서드
    @GetMapping("/kakao/callback")
    public String kakaoCallback(@RequestParam String code) {
        String kakaoAccessToken = loginService.requestAccessToken(code);
        return "카카오엑세스 토큰: " + kakaoAccessToken;
    }

    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginRequestDto requestDto) {
        String kakaoAccessToken = requestDto.getKakaoAccessToken();
        LoginResponseDto loginResponse = loginService.login(kakaoAccessToken);
        return CommonResponse.success(loginResponse, "로그인에 성공했습니다.");
    }
}
