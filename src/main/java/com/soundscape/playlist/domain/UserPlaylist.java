package com.soundscape.playlist.domain;

import com.soundscape.common.jpa.BaseTimeEntity;
import com.soundscape.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_playlists")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserPlaylist extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @Column(name = "custom_playlist_name", nullable = false)
    private String customPlaylistName;

    public UserPlaylist(User user, Playlist playlist, String customPlaylistName) {
        this.user = user;
        this.playlist = playlist;
        this.customPlaylistName = customPlaylistName;
    }

    public void updateCustomPlaylistName(String newName) {
        this.customPlaylistName = newName;
    }
}
