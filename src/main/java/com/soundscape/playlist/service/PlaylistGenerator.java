package com.soundscape.playlist.service;

import com.soundscape.common.exception.BaseException;
import com.soundscape.common.factory.SpotifyApiFactory;
import com.soundscape.common.response.ErrorCode;
import com.soundscape.playlist.api.dto.PlaylistResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import se.michaelthelin.spotify.SpotifyApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaylistGenerator {

    @Value("${spotify.service-user-id}")
    private String serviceUserId;
    @Value("${soundscape.music-agent}")
    private String musicAgentUrl;

    private final RestClient restClient;
    private final SpotifyApiFactory spotifyApiFactory;

    public PlaylistResponse createSpotifyPlaylist(PlaylistCommand command) {
        // 추천 엔진 호출
        EngineResponse engineResponse = callEngine(command);
        List<EngineResponse.TrackDto> engineTracks = engineResponse.getOutput().getFinal_tracks();

        SpotifyApi spotifyApi = spotifyApiFactory.getSpotifyApi();

        // 플레이리스트 이름 생성
        String playlistName = String.format("%s에 생성된 맞춤 플레이리스트",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        try {
            // 서비스 계정 권한으로 플레이리스트 생성
            var createRequest = spotifyApi.createPlaylist(serviceUserId, playlistName)
                    .description("Soundscape AI가 추천한 상황 맞춤 음악")
                    .public_(true)
                    .build();
            var spotifyPlaylist = createRequest.execute();

            // 수집된 곡들의 URI 리스트 추출
            String[] trackUris = engineTracks.stream()
                    .map(EngineResponse.TrackDto::getTu)
                    .toArray(String[]::new);

            // 생성된 플레이리스트에 곡 추가
            spotifyApi.addItemsToPlaylist(spotifyPlaylist.getId(), trackUris).build().execute();

            // PlaylistResponse 로 가공
            List<PlaylistResponse.Song> songs = engineTracks.stream()
                    .map(t -> PlaylistResponse.Song.builder()
                            .title(t.getTn())
                            .artistName(t.getAt().isEmpty() ? "Unknown" : t.getAt().get(0).getAtn())
                            .albumName(t.getAn())
                            .uri(t.getTu())
                            .spotifyUrl(t.getTurl())
                            .imageUrl(t.getImg())
                            .duration(formatDuration(t.getMs()))
                            .build())
                    .toList();

            return PlaylistResponse.builder()
                    .playlistName(playlistName)
                    .spotifyPlaylistId(spotifyPlaylist.getId())
                    .playlistUrl(spotifyPlaylist.getExternalUrls().get("spotify"))
                    .songs(songs)
                    .build();

        } catch (Exception e) {
            log.error("플레이리스트 생성 실패: {}", e.getMessage());
            throw new BaseException("스포티파이 플레이리스트 생성 중 오류 발생", ErrorCode.SPOTIFY_API_ERROR);
        }
    }

    private EngineResponse callEngine(PlaylistCommand command) {
        EngineRequest engineRequest = new EngineRequest(
                new EngineRequest.Input(
                        new EngineRequest.UserContext(
                                command.getLocation().toLowerCase(),
                                command.getGoal().toLowerCase(),
                                command.getDecibel().toLowerCase()
                        ),
                        List.of()
                )
        );

        return restClient.post()
                .uri(musicAgentUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(engineRequest)
                .retrieve()
                .body(EngineResponse.class);
    }

    private String formatDuration(int durationMs) {
        int minutes = (durationMs / 1000) / 60;
        int seconds = (durationMs / 1000) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
