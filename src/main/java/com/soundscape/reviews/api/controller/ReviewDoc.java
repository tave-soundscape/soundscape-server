package com.soundscape.reviews.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.reviews.api.dto.ReviewRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Review", description = "사용자 평가 API")
public interface ReviewDoc {

    @Operation(summary = "사용자 평가 등록 API", description = "사용자의 평가를 등록합니다.")
    CommonResponse createReview(ReviewRequest request);
}
