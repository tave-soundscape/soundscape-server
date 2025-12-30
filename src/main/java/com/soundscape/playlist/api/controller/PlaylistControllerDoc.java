package com.soundscape.playlist.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.playlist.api.dto.PlaylistNameUpdateRequest;
import com.soundscape.playlist.api.dto.PlaylistRequest;
import com.soundscape.playlist.api.dto.PlaylistResponse;
import com.soundscape.playlist.api.dto.SimplePlaylistsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Playlist", description = "플레이리스트 API")
public interface PlaylistControllerDoc {
    @Operation(summary = "플레이리스트 생성 API", description = "사용자 입력값을 바탕으로 스포티파이 플레이리스트를 생성합니다.")
    CommonResponse<PlaylistResponse> generatePlaylist(@RequestBody PlaylistRequest request);
    @Operation(summary = "플레이리스트 이름 수정 및 라이브러리 저장 API", description = "플레이리스트 이름을 수정하고 사용자의 라이브러리에 저장합니다.")
    CommonResponse savePlaylist(@PathVariable Long playlistId, @RequestBody PlaylistNameUpdateRequest newPlaylistName);
    @Operation(summary = "유저 플레이리스트 조회 API", description = "사용자 입력값을 바탕으로 스포티파이 플레이리스트를 생성합니다.")
    CommonResponse<SimplePlaylistsResponse> getUserPlaylists(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);
    @Operation(summary = "플레이리스트 상세 조회 API", description = "특정 플레이리스트의 상세 정보를 조회합니다.")
    CommonResponse getPlaylistDetails(@PathVariable Long playlistId);
}

