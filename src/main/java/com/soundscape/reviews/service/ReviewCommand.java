package com.soundscape.reviews.service;

import com.soundscape.reviews.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewCommand {

    private final Long userId;
    private final Integer rating;
    private final String dislikeReason;
    private final String preferredMood;
    private final String lyricsPreference;
    private final String feedback;
    private final Boolean willReuse;

    public Review toEntity() {
        return Review.builder()
                .rating(this.rating)
                .dislikeReason(this.dislikeReason)
                .preferredMood(this.preferredMood)
                .lyricsPreference(this.lyricsPreference)
                .feedback(this.feedback)
                .willReuse(this.willReuse)
                .build();
    }
}