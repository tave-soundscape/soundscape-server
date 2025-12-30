package com.soundscape.reviews.service;

import com.soundscape.reviews.domain.Review;
import com.soundscape.reviews.repository.ReviewRepository;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserReader userReader;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void createReview(ReviewCommand command) {
        User user = userReader.getUser(command.getUserId());
        Review review = command.toEntity();
        review.assignUser(user);
        reviewRepository.save(review);
    }
}