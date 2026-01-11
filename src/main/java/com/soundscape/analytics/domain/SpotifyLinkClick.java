package com.soundscape.analytics.domain;

import com.soundscape.common.jpa.BaseTimeEntity;
import com.soundscape.playlist.domain.Playlist;
import com.soundscape.playlist.domain.PlaylistCondition;
import com.soundscape.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "spotify_link_clicks")
@Getter
public class SpotifyLinkClick extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @Embedded
    private PlaylistCondition playlistCondition;

    @Column(name = "minutes_from_creation")
    private Long minutesFromCreation;

    @Builder
    public SpotifyLinkClick(User user, Playlist playlist, Long secondsFromCreation) {
        this.user = user;
        this.playlist = playlist;
        this.playlistCondition = playlist.getPlaylistCondition();
        this.minutesFromCreation = secondsFromCreation;
    }
}
