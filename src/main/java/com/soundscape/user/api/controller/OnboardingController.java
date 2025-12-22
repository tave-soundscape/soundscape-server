package com.soundscape.user.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.user.api.dto.OnboardingRequestDto;
import com.soundscape.user.service.OnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/onBoarding")
@RequiredArgsConstructor
public class OnboardingController {
    private final OnboardingService onboardingService;

    @PostMapping("/onboarding")
    public CommonResponse onboarding(@RequestParam Long userId,
                                           @RequestBody OnboardingRequestDto dto) {
        onboardingService.onboarding(userId, dto);
        // 여기도 어떤걸 리턴해야할지 모르겠어서 우선 이름 리턴했습니다
        return CommonResponse.success(dto.getNickname());
    }
}
