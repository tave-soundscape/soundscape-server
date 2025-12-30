package com.soundscape.reviews.api;

import com.soundscape.common.auth.context.UserContextHolder;
import com.soundscape.common.response.CommonResponse;
import com.soundscape.reviews.api.dto.ReviewRequest;
import com.soundscape.reviews.service.ReviewCommand;
import com.soundscape.reviews.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public CommonResponse createReview(ReviewRequest request) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        ReviewCommand command = request.toCommand(userId);
        reviewService.createReview(command);

        return CommonResponse.success(null, "리뷰가 정상 등록되었습니다");
    }
}
