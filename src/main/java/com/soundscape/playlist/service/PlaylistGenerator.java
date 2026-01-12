package com.soundscape.playlist.service;

import com.soundscape.playlist.api.dto.response.PlaylistResponse;
import com.soundscape.playlist.exception.SpotifyApiException;
import com.soundscape.playlist.infra.spotify.SpotifyPlaylistInfo;
import com.soundscape.playlist.infra.engine.MusicEngineClient;
import com.soundscape.playlist.infra.engine.dto.EngineResponse;
import com.soundscape.playlist.infra.spotify.SpotifyPlaylistClient;
import com.soundscape.playlist.service.command.PlaylistCommand;
import com.soundscape.playlist.service.mapper.PlaylistMapper;
import com.soundscape.playlist.service.util.PlaylistConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistGenerator {

    @Value("${spotify.service-user-id}")
    private String serviceUserId;

    private final MusicEngineClient musicEngineClient;
    private final SpotifyPlaylistClient spotifyPlaylistClient;

    public PlaylistResponse generate(PlaylistCommand command, List<String> favArtists) {
        // 추천 엔진 호출
        EngineResponse engineResponse = musicEngineClient.recommend(command, favArtists);
        List<EngineResponse.TrackDto> engineTracks = engineResponse.getOutput().getFinalTracks();

        // 플레이리스트 이름 생성
        String playlistName = String.format(
                PlaylistConstants.PLAYLIST_NAME_FORMAT,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(PlaylistConstants.PLAYLIST_DATE_FORMAT))
        );

        try {
            // 스포티파이 플레이리스트 생성
            SpotifyPlaylistInfo playlistInfo = spotifyPlaylistClient.createPlaylist(serviceUserId, playlistName);

            // 수집된 곡들의 URI 리스트 추출
            String[] trackUris = engineTracks.stream()
                    .map(EngineResponse.TrackDto::getTu)
                    .toArray(String[]::new);

            // 생성된 플레이리스트에 곡 추가
            spotifyPlaylistClient.addTracksToPlaylist(playlistInfo.getPlaylistId(), trackUris);

            // PlaylistResponse 로 가공
            List<PlaylistResponse.Song> songs = PlaylistMapper.toSongsFromEngine(engineTracks);

            return PlaylistResponse.builder()
                    .playlistName(playlistName)
                    .spotifyPlaylistId(playlistInfo.getPlaylistId())
                    .playlistUrl(playlistInfo.getPlaylistUrl())
                    .songs(songs)
                    .build();

        } catch (Exception e) {
            throw new SpotifyApiException("스포티파이 플레이리스트 생성 중 오류 발생");
        }
    }
}
