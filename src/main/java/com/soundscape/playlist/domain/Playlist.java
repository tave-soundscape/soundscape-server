package com.soundscape.playlist.domain;

import com.soundscape.common.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "playlists")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playlistName;

    @Column(name = "spotify_playlist_id")
    private String spotifyPlaylistId;

    @Column(length = 2048)
    private String playlistUrl;

    @Embedded
    private PlaylistCondition playlistCondition;

    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY)
    private List<UserPlaylist> userPlaylists = new ArrayList<>();

    @Builder
    public Playlist(String playlistName, String playlistUrl, String spotifyPlaylistId, PlaylistCondition playlistCondition) {
        this.playlistName = playlistName;
        this.playlistUrl = playlistUrl;
        this.spotifyPlaylistId = spotifyPlaylistId;
        this.playlistCondition = playlistCondition;
    }

    public void updatePlaylistName(String newPlaylistName) {
        this.playlistName = newPlaylistName;
    }
}
