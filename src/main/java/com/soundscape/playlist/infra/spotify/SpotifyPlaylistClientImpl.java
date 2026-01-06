package com.soundscape.playlist.infra.spotify;

import com.soundscape.common.factory.SpotifyApiFactory;
import com.soundscape.playlist.exception.SpotifyApiException;
import com.soundscape.playlist.infra.dto.SpotifyPlaylistInfo;
import com.soundscape.playlist.service.util.PlaylistConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Playlist;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpotifyPlaylistClientImpl implements SpotifyPlaylistClient {

    private final SpotifyApiFactory spotifyApiFactory;

    @Override
    public SpotifyPlaylistInfo createPlaylist(String userId, String playlistName) {
        SpotifyApi spotifyApi = spotifyApiFactory.getSpotifyApi();
        try {
            var createRequest = spotifyApi.createPlaylist(userId, playlistName)
                    .description(PlaylistConstants.DEFAULT_PLAYLIST_DESCRIPTION)
                    .public_(true)
                    .build();
            var spotifyPlaylist = createRequest.execute();

            return new SpotifyPlaylistInfo(
                    spotifyPlaylist.getId(),
                    spotifyPlaylist.getExternalUrls().get("spotify")
            );
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyApiException("스포티파이 플레이리스트 생성 중 오류 발생");
        }
    }

    @Override
    public void addTracksToPlaylist(String playlistId, String[] trackUris) {
        SpotifyApi spotifyApi = spotifyApiFactory.getSpotifyApi();
        try {
            spotifyApi.addItemsToPlaylist(playlistId, trackUris).build().execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("스포티파이 플레이리스트 트랙 추가 실패: {}", e.getMessage());
            throw new SpotifyApiException("스포티파이 플레이리스트 트랙 추가 중 오류 발생");
        }
    }

    @Override
    public Playlist getPlaylistDetails(String playlistId) {
        SpotifyApi spotifyApi = spotifyApiFactory.getSpotifyApi();
        try {
            return spotifyApi.getPlaylist(playlistId).build().execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("스포티파이 플레이리스트 조회 실패: {}", e.getMessage());
            throw new SpotifyApiException("플레이리스트 조회 중 오류 발생");
        }
    }

    @Override
    public void updatePlaylistName(String playlistId, String newName) {
        SpotifyApi spotifyApi = spotifyApiFactory.getSpotifyApi();
        try {
            spotifyApi.changePlaylistsDetails(playlistId)
                    .name(newName)
                    .build()
                    .execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.error("스포티파이 플레이리스트 이름 변경 실패: {}", e.getMessage());
            throw new SpotifyApiException("스포티파이 플레이리스트 이름 변경 중 오류 발생");
        }
    }
}