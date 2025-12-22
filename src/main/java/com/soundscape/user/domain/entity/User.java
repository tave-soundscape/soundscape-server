package com.soundscape.user.domain.entity;

import com.soundscape.common.jpa.BaseTimeEntity;
import com.soundscape.playlist.domain.Playlist;
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
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spotify_user_id")
    private SpotifyUser spotifyUser;

    private String username;

    @Convert(converter = ListToStringConverter.class)
    @Column(name = "fav_artists")
    private List<String> favArtists = new ArrayList<>();

    @Convert(converter = ListToStringConverter.class)
    @Column(name = "fav_genres")
    private List<String> favGenres = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist> playlists = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public void addFavArtist(String artist) {
        this.favArtists.add(artist);
    }

    public void addPlayList(Playlist playlist) {
        playlists.add(playlist);
        if (playlist.getUser() != this) {
            playlist.setUser(this);
        }
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

}
