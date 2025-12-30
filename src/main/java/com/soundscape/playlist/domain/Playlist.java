package com.soundscape.playlist.domain;

import com.soundscape.common.jpa.BaseTimeEntity;
import com.soundscape.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "playlists")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playlistName;

    @Column(length = 2048)
    private String playlistUrl;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Playlist(String playlistName, String playlistUrl) {
        this.playlistName = playlistName;
        this.playlistUrl = playlistUrl;
        this.user = user;
    }

    public void updatePlaylistName(String newPlaylistName) {
        this.playlistName = newPlaylistName;
    }
}
