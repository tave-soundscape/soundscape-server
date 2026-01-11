package com.soundscape.analytics.api.controller;

import com.soundscape.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Analytics", description = "데이터분석 API")
public interface AnalyticsControllerDoc {
    @Operation(summary = "플레이리스트 클릭 기록 API", description = "사용자가 플레이리스트의 스포티파이 링크를 클릭한 기록을 남깁니다.")
    public CommonResponse recordClick(@PathVariable Long playlistId);
}
