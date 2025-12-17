package com.soundscape.playlist.service;

import com.soundscape.playlist.api.dto.PlaylistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PlaylistGenerator {

    // 현재는 고정된 임시 플레이리스트 반환
    public PlaylistResponse createSpotifyPlaylist() {
        EngineResposne engineResposne = callEngine();

        // TODO 엔진 응답을 바탕으로 스포티파이 플레이리스트 생성 로직 구현

        return PlaylistResponse.builder()
                .playlistName("임시 플레이리스트")
                .playlistUrl("https://open.spotify.com/playlist/3LgIAaE7ut3yL8s2v7JHNu?si=5f3e632c480e4a10")
                .songs(createTempSongs())
                .build();
    }

    // TODO 엔진 연동 후 로직 구현, 현재는 고정된 임시 플레이리스트 반환
    private EngineResposne callEngine() {
        // 플레이리스트 생성 로직 구현
        return null;
    }

    static class EngineResposne {
        // TODO 엔진 응답 형식 결정된 후 구현
    }

    // TODO: 임시 노래 리스트 생성, 엔진 연동 후 삭제 예정
    private List<PlaylistResponse.Song> createTempSongs() {
        return IntStream.range(0, 10)
                .mapToObj(i -> PlaylistResponse.Song.builder()
                        .title("Supersonic")
                        .artistName("fromis_9")
                        .albumName("Supersonic")
                        .uri("spotify:track:6oNLSQX8bcAdbCElZYju3v")
                        .spotifyUrl("https://open.spotify.com/track/6oNLSQX8bcAdbCElZYju3v")
                        .imageUrl("https://i.scdn.co/image/ab67616d0000b2730490e8ab48790ca6e5add267")
                        .duration("2:54")
                        .build()
                )
                .toList();
    }
}
