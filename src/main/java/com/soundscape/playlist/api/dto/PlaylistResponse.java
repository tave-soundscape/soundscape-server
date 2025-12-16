package com.soundscape.playlist.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "플레이리스트 생성 응답 DTO")
@Getter
public class PlaylistResponse {

    @Schema(description = "플레이리스트 이름", example = "2025-12-16 17시 30분에 생성된 [사용자이름]의 플레이리스트")
    private final String playlistName;
    @Schema(description = "스포티파이 플레이리스트 URL", example = "https://open.spotify.com/playlist/3LgIAaE7ut3yL8s2v7JHNu?si=5f3e632c480e4a10")
    private final String playlistUrl;
    @Schema(description = "플레이리스트에 포함된 노래 목록")
    private final List<Song> songs;

    @Builder
    public PlaylistResponse(String playlistName, String playlistUrl, List<Song> songs) {
        this.playlistName = playlistName;
        this.playlistUrl = playlistUrl;
        this.songs = songs;
    }

    @Schema(description = "노래 정보 DTO")
    @Getter
    @Builder
    public static class Song {
        @Schema(description = "노래 제목", example = "Supersonic")
        private final String title;
        @Schema(description = "아티스트 이름", example = "fromis_9")
        private final String artistName;
        @Schema(description = "앨범 이름", example = "Supersonic")
        private final String albumName;
        @Schema(description = "스포티파이 URI, 딥링크 연결용", example = "spotify:track:6oNLSQX8bcAdbCElZYju3v")
        private final String uri;
        @Schema(description = "스포티파이 노래 URL", example = "https://open.spotify.com/track/6oNLSQX8bcAdbCElZYju3v")
        private final String spotifyUrl;
        @Schema(description = "앨범 이미지 URL", example = "https://i.scdn.co/image/ab67616d0000b2730490e8ab48790ca6e5add267")
        private final String imageUrl;
        @Schema(description = "노래 재생 시간", example = "2:54")
        private final String duration;
    }
}
