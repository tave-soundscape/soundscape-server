package com.soundscape.user.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.user.domain.dto.LoginRequestDto;
import com.soundscape.user.domain.dto.LoginResponseDto;
import com.soundscape.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping // TODO: 추후 삭제 - 스포티파이 인증 코드 테스트용 메서드
    public CommonResponse getAuthorizationCode() {
        String authorizationUrl = loginService.getLoginURI();
        return CommonResponse.success(authorizationUrl);
    }

    @PostMapping("/login")
    public CommonResponse getAccessToken(@RequestBody LoginRequestDto requestDto) {
        String userCode = requestDto.getCode();
        LoginResponseDto loginResponse = loginService.login(userCode);
        return CommonResponse.success(loginResponse);
    }
}
