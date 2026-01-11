package com.soundscape.analytics.api.controller;

import com.soundscape.analytics.service.AnalyticsService;
import com.soundscape.common.auth.context.UserContextHolder;
import com.soundscape.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController implements AnalyticsControllerDoc{

    private final AnalyticsService analyticsService;

    @PostMapping("/{playlistId}")
    public CommonResponse recordClick(@PathVariable Long playlistId) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        analyticsService.recordClick(userId, playlistId);
        return CommonResponse.success(null, "Spotify 링크 클릭이 기록되었습니다.");
    }
}
