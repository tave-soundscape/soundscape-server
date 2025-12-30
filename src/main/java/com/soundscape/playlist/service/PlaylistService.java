package com.soundscape.playlist.service;

import com.soundscape.common.exception.EntityNotFoundException;
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
        Playlist initPlaylist = new Playlist(result.getPlaylistName(), result.getPlaylistUrl());
        Playlist playlist = playlistRepository.save(initPlaylist);

        return PlaylistResponse.builder()
                .playlistId(playlist.getId())
                .playlistName(result.getPlaylistName())
                .playlistUrl(result.getPlaylistUrl())
                .songs(result.getSongs())
                .build();
    }

    @Transactional
    public void savePlaylist(Long playlistId, Long userId, String newPlaylistName) {
        User user = userReader.getUser(userId);
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found with id: " + playlistId));
        playlist.updatePlaylistName(newPlaylistName);
        user.addPlayList(playlist);
        playlistRepository.save(playlist);
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