package com.soundscape.reviews.domain;

import com.soundscape.common.jpa.BaseTimeEntity;
import com.soundscape.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer rating; // 유저 Rating 0,1,2,3,4 (매우나쁨 ~ 매우좋음)
    private String dislikeReason;
    private String preferredMood;
    private String lyricsPreference;
    @Lob
    private String feedback;
    private Boolean willReuse;

    @Builder
    public Review(User user, Integer rating, String dislikeReason, String preferredMood, String lyricsPreference, String feedback, Boolean willReuse) {
        this.user = user;
        this.rating = rating;
        this.dislikeReason = dislikeReason;
        this.preferredMood = preferredMood;
        this.lyricsPreference = lyricsPreference;
        this.feedback = feedback;
        this.willReuse = willReuse;
    }

    public void assignUser(User user) {
        this.user = user;
    }
}
