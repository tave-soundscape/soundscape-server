package com.soundscape.playlist.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PlaylistExploreListResponse {
    private final List<PlaylistExploreResponse> playlists;
    private final boolean hasNext;

    @Getter
    @AllArgsConstructor
    public static class PlaylistExploreResponse {
        @Schema(description = "Playlist ID, 서버 데이터베이스에 저장된 플레이리스트의 id 입니다.", example = "123")
        private final Long playlistId;
        @Schema(description = "서버에서 생성한 플레이리스트의 제목입니다.", example = "2026-01-01 13:28에 생성된 맞춤 플레이리스트")
        private final String playlistName;
        @Schema(description = "플레이리스트 스포티파이 url 입니다.", example = "https://open.spotify.com/playlist/713inr1wX8wVOuCzqtCAV0")
        private final String playlistUrl;
        @Schema(description = "플레이리스트가 저장한 사용자의 수입니다.", example = "42")
        private final int saveCount;
    }
}