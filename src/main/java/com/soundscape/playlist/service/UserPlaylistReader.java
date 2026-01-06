package com.soundscape.playlist.service;

import com.soundscape.playlist.domain.Playlist;
import com.soundscape.playlist.domain.UserPlaylist;
import com.soundscape.playlist.exception.UserPlaylistNotFoundException;
import com.soundscape.playlist.repository.UserPlaylistRepository;
import com.soundscape.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPlaylistReader {

    private final UserPlaylistRepository userPlaylistRepository;

    public boolean checkExistence(User user, Playlist playlist) {
        return userPlaylistRepository.existsByUserAndPlaylist(user, playlist);
    }

    public UserPlaylist getUserPlaylist(User user, Playlist playlist) {
        return userPlaylistRepository.findByUserAndPlaylist(user, playlist)
                .orElseThrow(() -> new UserPlaylistNotFoundException("해당 유저 플레이리스트가 존재하지 않습니다."));
    }
}
