package com.soundscape.playlist.api.controller;

import com.soundscape.playlist.api.dto.PlaylistRequest;
import com.soundscape.playlist.api.dto.PlaylistResponse;
import com.soundscape.playlist.api.dto.SimplePlaylistsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Playlist", description = "플레이리스트 API")
public interface PlaylistControllerDoc {
    @Operation(summary = "플레이리스트 생성 API", description = "사용자 입력값을 바탕으로 스포티파이 플레이리스트를 생성합니다.")
    PlaylistResponse generatePlaylist(@RequestBody PlaylistRequest request);
    @Operation(summary = "유저 플레이리스트 조회 API", description = "사용자 입력값을 바탕으로 스포티파이 플레이리스트를 생성합니다.")
    SimplePlaylistsResponse getUserPlaylists(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);
}

