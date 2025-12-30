package com.soundscape.reviews.api.dto;

import com.soundscape.reviews.service.ReviewCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewRequest {

    private final Integer rating;
    private final String dislikeReason;
    private final String preferredMood;
    private final String lyricsPreference;
    private final String feedback;
    private final Boolean willReuse;

    public ReviewCommand toCommand(Long userId) {
        return new ReviewCommand(
                userId,
                this.rating,
                this.dislikeReason,
                this.preferredMood,
                this.lyricsPreference,
                this.feedback,
                this.willReuse
        );
    }
}
