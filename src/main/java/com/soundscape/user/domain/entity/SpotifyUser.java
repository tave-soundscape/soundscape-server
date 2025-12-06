package com.soundscape.user.domain.entity;

import com.soundscape.common.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "spotify_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SpotifyUser extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spotify_id")
    private String spotifyId;

    @Column(name = "spotify_user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "access_token", columnDefinition = "TEXT")
    private String accessToken;

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;

    @Builder
    public SpotifyUser(String spotifyId, String userName, String email, String accessToken, String refreshToken) {
        this.spotifyId = spotifyId;
        this.userName = userName;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
