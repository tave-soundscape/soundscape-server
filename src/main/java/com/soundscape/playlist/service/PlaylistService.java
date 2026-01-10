package com.soundscape.playlist.service;

import com.soundscape.playlist.api.dto.response.PlaylistExploreListResponse;
import com.soundscape.playlist.api.dto.response.PlaylistResponse;
import com.soundscape.playlist.api.dto.response.SimplePlaylistsResponse;
import com.soundscape.playlist.domain.Playlist;
import com.soundscape.playlist.domain.PlaylistCondition;
import com.soundscape.playlist.domain.UserPlaylist;
import com.soundscape.playlist.infra.spotify.SpotifyPlaylistClient;
import com.soundscape.playlist.repository.PlaylistRepository;
import com.soundscape.playlist.repository.UserPlaylistRepository;
import com.soundscape.playlist.service.command.PlaylistCommand;
import com.soundscape.playlist.service.mapper.PlaylistMapper;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistGenerator playlistGenerator;
    private final UserReader userReader;
    private final PlaylistReader playlistReader;
    private final UserPlaylistReader userPlaylistReader;
    private final PlaylistRepository playlistRepository;
    private final UserPlaylistRepository userPlaylistRepository;
    private final SpotifyPlaylistClient spotifyPlaylistClient;

    @Transactional
    public PlaylistResponse generatePlaylist(PlaylistCommand command) {

        PlaylistResponse result = playlistGenerator.generate(command);
        PlaylistCondition playlistCondition = new PlaylistCondition(command.getLocation(), command.getDecibel(), command.getGoal());
        Playlist playlist = new Playlist(
                result.getPlaylistName(),
                result.getPlaylistUrl(),
                result.getSpotifyPlaylistId(),
                playlistCondition
        );
        playlistRepository.save(playlist);

        return PlaylistResponse.builder()
                .playlistId(playlist.getId())
                .playlistName(result.getPlaylistName())
                .location(command.getLocation())
                .goal(command.getGoal())
                .spotifyPlaylistId(result.getSpotifyPlaylistId())
                .playlistUrl(result.getPlaylistUrl())
                .songs(result.getSongs())
                .build();
    }

    @Transactional
    public void savePlaylist(Long playlistId, Long userId, String newPlaylistName) {
        User user = userReader.getUser(userId);
        Playlist playlist = playlistReader.getPlaylist(playlistId);
        boolean existence = userPlaylistReader.checkExistence(user, playlist);
        if (existence) {
            UserPlaylist userPlaylist = userPlaylistReader.getUserPlaylist(user, playlist);
            userPlaylist.updateCustomPlaylistName(newPlaylistName);
        } else {
            UserPlaylist userPlaylist = new UserPlaylist(user, playlist, newPlaylistName);
            userPlaylistRepository.save(userPlaylist);
        }
    }

    @Transactional
    public void unsavePlaylist(Long userId, Long playlistId) {
        User user = userReader.getUser(userId);
        Playlist playlist = playlistReader.getPlaylist(playlistId);
        UserPlaylist userPlaylist = userPlaylistReader.getUserPlaylist(user, playlist);
        userPlaylistRepository.delete(userPlaylist);
    }

    @Transactional(readOnly = true)
    public PlaylistResponse getPlaylistDetails(Long playlistId, Long userId) {
        Playlist playlist = playlistReader.getPlaylist(playlistId);
        String spotifyPlaylistId = playlist.getSpotifyPlaylistId();
        var spotifyPlaylistDetails = fetchSpotifyPlaylist(spotifyPlaylistId);

        String playlistName = playlist.getPlaylistName();
        if (userId != null) {
            User user = userReader.getUser(userId);
            boolean isSaved = userPlaylistReader.checkExistence(user, playlist);
            if (isSaved) {
                UserPlaylist userPlaylist = userPlaylistReader.getUserPlaylist(user, playlist);
                playlistName = userPlaylist.getCustomPlaylistName();
            }
        }

        List<PlaylistResponse.Song> songs = Arrays.stream(spotifyPlaylistDetails.getTracks().getItems())
                .map(item -> {
                    var itemElement = item.getTrack();
                    if (itemElement instanceof Track track) {
                        return PlaylistMapper.toSongFromSpotifyTrack(track);
                    }

                    return null;
                })
                .filter(java.util.Objects::nonNull) // Track이 아닌 것들은 걸러냄
                .toList();

        return PlaylistResponse.builder()
                .playlistId(playlist.getId())
                .playlistName(playlistName)
                .location(playlist.getPlaylistCondition().getLocation())
                .goal(playlist.getPlaylistCondition().getGoal())
                .spotifyPlaylistId(spotifyPlaylistId)
                .playlistUrl(playlist.getPlaylistUrl())
                .songs(songs)
                .build();
    }

    @Transactional(readOnly = true)
    public SimplePlaylistsResponse getUserPlaylists(Long userId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        User user = userReader.getUser(userId);
        Slice<UserPlaylist> userPlaylists = userPlaylistRepository.findAllByUser(user, pageable);
        List<SimplePlaylistsResponse.SimpleInfo> simpleList = userPlaylists.getContent().stream()
                .map(SimplePlaylistsResponse.SimpleInfo::new)
                .toList();

        return new SimplePlaylistsResponse(simpleList, userPlaylists.hasNext());
    }

    @Transactional(readOnly = true)
    public PlaylistExploreListResponse exploreByLocation(String location, Pageable pageable) {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        Slice<Playlist> playlists = playlistRepository.findByLocation(location, oneDayAgo, pageable);
        List<PlaylistExploreListResponse.PlaylistExploreResponse> exploreList = playlists.getContent().stream()
                .map(playlist -> {
                    int saveCount = playlistRepository.countSavesByPlaylistId(playlist.getId());
                    return new PlaylistExploreListResponse.PlaylistExploreResponse(
                            playlist.getId(),
                            playlist.getPlaylistName(),
                            playlist.getPlaylistUrl(),
                            saveCount
                    );
                })
                .toList();

        return new PlaylistExploreListResponse(exploreList, playlists.hasNext());
    }

    @Transactional(readOnly = true)
    public PlaylistExploreListResponse exploreByGoal(String goal, Pageable pageable) {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        Slice<Playlist> playlists = playlistRepository.findByGoal(goal, oneDayAgo, pageable);
        List<PlaylistExploreListResponse.PlaylistExploreResponse> exploreList = playlists.getContent().stream()
                .map(playlist -> {
                    int saveCount = playlistRepository.countSavesByPlaylistId(playlist.getId());
                    return new PlaylistExploreListResponse.PlaylistExploreResponse(
                            playlist.getId(),
                            playlist.getPlaylistName(),
                            playlist.getPlaylistUrl(),
                            saveCount
                    );
                })
                .toList();

        return new PlaylistExploreListResponse(exploreList, playlists.hasNext());
    }

    @Transactional(readOnly = true)
    public PlaylistExploreListResponse exploreByDecibel(String decibel, Pageable pageable) {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        Slice<Playlist> playlists = playlistRepository.findByDecibel(decibel, oneDayAgo, pageable);
        List<PlaylistExploreListResponse.PlaylistExploreResponse> exploreList = playlists.getContent().stream()
                .map(playlist -> {
                    int saveCount = playlistRepository.countSavesByPlaylistId(playlist.getId());
                    return new PlaylistExploreListResponse.PlaylistExploreResponse(
                            playlist.getId(),
                            playlist.getPlaylistName(),
                            playlist.getPlaylistUrl(),
                            saveCount
                    );
                })
                .toList();

        return new PlaylistExploreListResponse(exploreList, playlists.hasNext());
    }

    private se.michaelthelin.spotify.model_objects.specification.Playlist fetchSpotifyPlaylist(String spotifyPlaylistId) {
        return spotifyPlaylistClient.getPlaylistDetails(spotifyPlaylistId);
    }
}