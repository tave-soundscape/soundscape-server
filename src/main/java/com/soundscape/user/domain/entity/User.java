package com.soundscape.user.domain.entity;

import com.soundscape.common.jpa.BaseTimeEntity;
import com.soundscape.playlist.domain.UserPlaylist;
import com.soundscape.user.domain.converter.ListToStringConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String oid; // kakao oauth 게정 id

    @Column(name = "user_name")
    private String username;

    @Convert(converter = ListToStringConverter.class)
    @Column(name = "fav_artists")
    private List<String> favArtists = new ArrayList<>();

    @Convert(converter = ListToStringConverter.class)
    @Column(name = "fav_genres")
    private List<String> favGenres = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserPlaylist> userPlaylists = new ArrayList<>();

    @Column(name = "is_onboarded", nullable = false)
    private boolean isOnboarded;

    public User(String oid) {
        this.oid = oid;
        this.isOnboarded = false;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateFavArtists(List<String> favArtists) {
        this.favArtists = favArtists;
    }

    public void updateFavGenres(List<String> favGenres) {
        this.favGenres = favGenres;
    }

    public void markOnboarded() {
        this.isOnboarded = true;
    }
}
