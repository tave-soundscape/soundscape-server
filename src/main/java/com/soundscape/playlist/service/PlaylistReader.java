package com.soundscape.playlist.service;

import com.soundscape.playlist.domain.Playlist;
import com.soundscape.playlist.exception.PlaylistNotFoundException;
import com.soundscape.playlist.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaylistReader {

    private final PlaylistRepository playlistRepository;

    public Playlist getPlaylist(Long playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + playlistId));
    }
}
