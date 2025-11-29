package com.soundscape.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    // src 아래에 넣는게 맞는건가요?
    // 언래 빌드에 넣어져있었는데 찾아보니 빌드에 넣으면 새로 빌드할 때마다 초기화돼서 src 아래에 넣어야한다고 해서 우선 src에 넣어뒀습니다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Spotify 고유 계정 ID
    @Column(name = "spotify_account_id", unique = true)
    private String spotifyAccountId;

    // Spotify 이메일 - 필요한가요?
    private String spotifyEmail;

    // 대표 프로필 이미지 - 필요한가요?
    private String profileImageUrl;

    // Spotify 표시 이름 - 필요한가요?
    @Column(name = "spotify_display_name")
    private String spotifyDisplayName;

    @Column(name = "spotify_refresh_token")
    private String refreshToken;

    // 그냥 string 형태로만 저장해두면 되는건지?
    @ElementCollection
    @CollectionTable(name = "user_fav_artists", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "artist")
    private List<String> favArtists = new ArrayList<>();

    // 그냥 string 형태로만 저장해두면 되는건지?
    @ElementCollection
    @CollectionTable(name = "user_fav_genres", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "genre")
    private List<String> favGenres = new ArrayList<>();

    // 유저 관리할 때 생성된 시간, 업데이트된 날짜 필드가 있으면 좋다고 하는데 저희 프로젝트에서도 있으면 좋을까요?
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}