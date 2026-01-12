package com.soundscape.playlist.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.playlist.api.dto.request.PlaylistNameUpdateRequest;
import com.soundscape.playlist.api.dto.request.PlaylistRequest;
import com.soundscape.playlist.api.dto.response.PlaylistExploreListResponse;
import com.soundscape.playlist.api.dto.response.PlaylistResponse;
import com.soundscape.playlist.api.dto.response.SimplePlaylistsResponse;
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
    @Operation(summary = "플레이리스트 라이브러리에서 삭제 API", description = "사용자의 라이브러리에서 플레이리스트를 삭제합니다.")
    public CommonResponse unsavePlaylist(@PathVariable Long playlistId);
    @Operation(summary = "유저 플레이리스트 조회 API", description = "사용자 입력값을 바탕으로 스포티파이 플레이리스트를 생성합니다.")
    CommonResponse<SimplePlaylistsResponse> getUserPlaylists(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);
    @Operation(summary = "플레이리스트 상세 조회 API", description = "특정 플레이리스트의 상세 정보를 조회합니다.")
    CommonResponse getPlaylistDetails(@PathVariable Long playlistId);
    @Operation(summary = "장소별 플레이리스트 탐색 API", description = "특정 장소와 관련된 플레이리스트를 탐색합니다.<br><br>가능한 값들: <br>gym, cafe, moving, library, home, park, co-working")
    CommonResponse<PlaylistExploreListResponse> exploreByLocation(@PathVariable String location, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);
    @Operation(summary = "목표별 플레이리스트 탐색 API", description = "특정 목표와 관련된 플레이리스트를 탐색합니다.<br><br>가능한 값들: <br>quiet, moderate, loud")
    CommonResponse<PlaylistExploreListResponse> exploreByGoal(@PathVariable String goal, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);
    @Operation(summary = "소음 정도별 플레이리스트 탐색 API", description = "특정 소음 정도와 관련된 플레이리스트를 탐색합니다.<br><br>가능한 값들: <br>focus, relax, sleep, active, anger, consolation, neutral")
    CommonResponse<PlaylistExploreListResponse> exploreByDecibel(@PathVariable String decibel, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);
}

