package com.soundscape.user.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.user.api.dto.OnboardingRequestDto;
import com.soundscape.user.service.OnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
public class OnboardingController {
    private final OnboardingService onboardingService;

    @PostMapping
    public CommonResponse onboarding(@RequestParam Long userId,
                                           @RequestBody OnboardingRequestDto dto) {
        onboardingService.onboarding(userId, dto);
        return CommonResponse.success("온보딩 정보 입력 성공");
    }
}
