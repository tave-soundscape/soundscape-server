package com.soundscape.playlist.service;

import com.soundscape.playlist.api.dto.PlaylistResponse;
import com.soundscape.playlist.domain.Playlist;
import com.soundscape.playlist.repository.PlaylistRepository;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistGenerator playlistGenerator;
    private final UserReader userReader;
    private final PlaylistRepository playlistRepository;

    @Transactional
    public PlaylistResponse generatePlaylist(Long userId) {
        PlaylistResponse result = playlistGenerator.createSpotifyPlaylist();
        Playlist initPlaylist = new Playlist(result.getPlaylistName(), result.getPlaylistUrl());
        Playlist playlist = playlistRepository.save(initPlaylist);
        User user = userReader.getUser(userId);
        user.addPlayList(playlist);
        return result;
    }
}
