package com.soundscape.analytics.service;

import com.soundscape.analytics.domain.SpotifyLinkClick;
import com.soundscape.analytics.repository.SpotifyLinkClickRepository;
import com.soundscape.playlist.domain.Playlist;
import com.soundscape.playlist.service.PlaylistReader;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final SpotifyLinkClickRepository spotifyLinkClickRepository;
    private final UserReader userReader;
    private final PlaylistReader playlistReader;

    public void recordClick(Long userId, Long playlistId) {
        User user = userReader.getUser(userId);
        Playlist playlist = playlistReader.getPlaylist(playlistId);

        long secondsFromCreation = ChronoUnit.MINUTES.between(
                playlist.getCreatedAt(),
                LocalDateTime.now()
        );

        SpotifyLinkClick click = SpotifyLinkClick.builder()
                .user(user)
                .playlist(playlist)
                .secondsFromCreation(secondsFromCreation)
                .build();

        spotifyLinkClickRepository.save(click);
    }
}
