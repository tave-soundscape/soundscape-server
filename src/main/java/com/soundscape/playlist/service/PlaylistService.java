package com.soundscape.playlist.service;

import com.soundscape.playlist.api.dto.PlaylistResponse;
import com.soundscape.playlist.api.dto.SimplePlaylistsResponse;
import com.soundscape.playlist.domain.Playlist;
import com.soundscape.playlist.repository.PlaylistRepository;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistGenerator playlistGenerator;
    private final UserReader userReader;
    private final PlaylistRepository playlistRepository;

    @Transactional
    public PlaylistResponse generatePlaylist(Long userId) {
        PlaylistResponse result = playlistGenerator.createSpotifyPlaylist();
        User user = userReader.getUser(userId);
        Playlist playlist = new Playlist(result.getPlaylistName(), result.getPlaylistUrl(), user);
        playlistRepository.save(playlist);
        user.addPlayList(playlist);

        return result;
    }

    @Transactional(readOnly = true)
    public SimplePlaylistsResponse getUserPlaylists(Long userId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        User user = userReader.getUser(userId);
        Slice<Playlist> userPlaylists = playlistRepository.findAllByUser(user, pageable);
        List<SimplePlaylistsResponse.SimpleInfo> simpleList = userPlaylists.getContent().stream()
                .map(SimplePlaylistsResponse.SimpleInfo::new)
                .toList();

        return new SimplePlaylistsResponse(simpleList, userPlaylists.hasNext());
    }
}