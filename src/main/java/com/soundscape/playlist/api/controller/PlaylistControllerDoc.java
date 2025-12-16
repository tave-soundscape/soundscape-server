package com.soundscape.playlist.api.controller;

import com.soundscape.playlist.api.dto.PlaylistRequest;
import com.soundscape.playlist.api.dto.PlaylistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Playlist", description = "플레이리스트 API")
public interface PlaylistControllerDoc {
    @Operation(summary = "플레이리스트 생성 API", description = "사용자 입력값을 바탕으로 스포티파이 플레이리스트를 생성합니다.")
    PlaylistResponse generatePlaylist(PlaylistRequest request);
}

