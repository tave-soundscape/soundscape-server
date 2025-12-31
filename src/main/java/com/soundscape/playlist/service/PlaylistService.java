package com.soundscape.playlist.service;

import com.soundscape.common.exception.BaseException;
import com.soundscape.common.factory.SpotifyApiFactory;
import com.soundscape.common.response.ErrorCode;
import com.soundscape.playlist.api.dto.PlaylistRequest;
import com.soundscape.playlist.api.dto.PlaylistResponse;
import com.soundscape.playlist.api.dto.SimplePlaylistsResponse;
import com.soundscape.playlist.domain.Playlist;
import com.soundscape.playlist.repository.PlaylistRepository;
import com.soundscape.user.domain.entity.User;
import com.soundscape.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final SpotifyApiFactory spotifyApiFactory;
    private final PlaylistGenerator playlistGenerator;
    private final UserReader userReader;
    private final PlaylistReader playlistReader;
    private final PlaylistRepository playlistRepository;

    @Transactional
    public PlaylistResponse generatePlaylist(Long userId, PlaylistRequest request) {
        PlaylistResponse result = playlistGenerator.createSpotifyPlaylist(request);
        Playlist initPlaylist = new Playlist(result.getPlaylistName(), result.getPlaylistUrl(), result.getSpotifyPlaylistId());
        Playlist playlist = playlistRepository.save(initPlaylist);

        return PlaylistResponse.builder()
                .playlistId(playlist.getId())
                .playlistName(result.getPlaylistName())
                .spotifyPlaylistId(result.getSpotifyPlaylistId())
                .playlistUrl(result.getPlaylistUrl())
                .songs(result.getSongs())
                .build();
    }

    @Transactional
    public void savePlaylist(Long playlistId, Long userId, String newPlaylistName) {
        User user = userReader.getUser(userId);
        Playlist playlist = playlistReader.getPlaylist(playlistId);
        String spotifyPlaylistId = playlist.getSpotifyPlaylistId();
        playlist.updatePlaylistName(newPlaylistName);
        user.addPlayList(playlist);

        try {
            spotifyApiFactory.getSpotifyApi().changePlaylistsDetails(spotifyPlaylistId)
                    .name(newPlaylistName)
                    .build()
                    .execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new BaseException("스포티파이 플레이리스트 이름 변경 중 오류 발생", ErrorCode.SPOTIFY_API_ERROR);
        }

        playlistRepository.save(playlist);
    }

    @Transactional(readOnly = true)
    public PlaylistResponse getPlaylistDetails(Long playlistId) {
        Playlist playlist = playlistReader.getPlaylist(playlistId);
        String spotifyPlaylistId = playlist.getSpotifyPlaylistId();
        var spotifyPlaylistDetails = fetchSpotifyPlaylist(spotifyPlaylistId);

        List<PlaylistResponse.Song> songs = Arrays.stream(spotifyPlaylistDetails.getTracks().getItems())
                .map(item -> {
                    var itemElement = item.getTrack();

                    if (itemElement instanceof Track track) {
                        return PlaylistResponse.Song.builder()
                                .title(track.getName())
                                .artistName(track.getArtists().length > 0 ? track.getArtists()[0].getName() : "Unknown")
                                .albumName(track.getAlbum().getName())
                                .uri(track.getUri())
                                .spotifyUrl(track.getExternalUrls().get("spotify"))
                                .imageUrl(track.getAlbum().getImages().length > 0 ? track.getAlbum().getImages()[0].getUrl() : null)
                                .duration(formatDuration(track.getDurationMs()))
                                .build();
                    }

                    return null;
                })
                .filter(java.util.Objects::nonNull) // Track이 아닌 것들은 걸러냄
                .toList();

        return PlaylistResponse.builder()
                .playlistId(playlist.getId())
                .playlistName(playlist.getPlaylistName())
                .spotifyPlaylistId(spotifyPlaylistId)
                .playlistUrl(playlist.getPlaylistUrl())
                .songs(songs)
                .build();
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

    private se.michaelthelin.spotify.model_objects.specification.Playlist fetchSpotifyPlaylist(String spotifyPlaylistId) {
        SpotifyApi api = spotifyApiFactory.getSpotifyApi();
        try {
            return api.getPlaylist(spotifyPlaylistId).build().execute();
        } catch (Exception e) {
            throw new BaseException("플레이리스트 조회 중 오류 발생", ErrorCode.SPOTIFY_API_ERROR);
        }
    }

    private String formatDuration(int durationMs) {
        int minutes = (durationMs / 1000) / 60;
        int seconds = (durationMs / 1000) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}