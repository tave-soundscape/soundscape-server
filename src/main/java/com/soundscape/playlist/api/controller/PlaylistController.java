package com.soundscape.playlist.api.controller;

import com.soundscape.common.auth.context.UserContextHolder;
import com.soundscape.common.response.CommonResponse;
import com.soundscape.playlist.api.dto.request.PlaylistNameUpdateRequest;
import com.soundscape.playlist.api.dto.request.PlaylistRequest;
import com.soundscape.playlist.api.dto.response.PlaylistExploreListResponse;
import com.soundscape.playlist.api.dto.response.PlaylistResponse;
import com.soundscape.playlist.api.dto.response.SimplePlaylistsResponse;
import com.soundscape.playlist.service.PlaylistService;
import com.soundscape.playlist.service.command.PlaylistCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/playlists")
@RequiredArgsConstructor
public class PlaylistController implements PlaylistControllerDoc {

    private final PlaylistService playlistService;

    @PostMapping
    public CommonResponse<PlaylistResponse> generatePlaylist(@RequestBody PlaylistRequest request) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        PlaylistCommand command = request.toCommand();
        PlaylistResponse result = playlistService.generatePlaylist(userId, command);
        return CommonResponse.success(result);
    }

    @PostMapping("/{playlistId}")
    public CommonResponse savePlaylist(@PathVariable Long playlistId, @RequestBody PlaylistNameUpdateRequest newPlaylistName) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        playlistService.savePlaylist(playlistId, userId, newPlaylistName.getNewPlaylistName());
        return CommonResponse.success("playlist ID: " + playlistId, "플레이리스트가 저장되었습니다.");
    }

    @DeleteMapping("/{playlistId}")
    public CommonResponse unsavePlaylist(@PathVariable Long playlistId) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        playlistService.unsavePlaylist(userId, playlistId);
        return CommonResponse.success("playlist ID: " + playlistId, "유저의 저장된 플레이리스트에서 삭제 되었습니다.");
    }

    @GetMapping
    public CommonResponse<SimplePlaylistsResponse> getUserPlaylists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        SimplePlaylistsResponse result = playlistService.getUserPlaylists(userId, page, size);
        return CommonResponse.success(result);
    }

    @GetMapping("/{playlistId}")
    public CommonResponse getPlaylistDetails(@PathVariable Long playlistId) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        PlaylistResponse result = playlistService.getPlaylistDetails(playlistId, userId);
        return CommonResponse.success(result);
    }

    @GetMapping("/explore/location/{location}")
    public CommonResponse<PlaylistExploreListResponse> exploreByLocation(
            @PathVariable String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PlaylistExploreListResponse result = playlistService.exploreByLocation(location, pageable);
        return CommonResponse.success(result);
    }

    @GetMapping("/explore/goal/{goal}")
    public CommonResponse<PlaylistExploreListResponse> exploreByGoal(
            @PathVariable String goal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PlaylistExploreListResponse result = playlistService.exploreByGoal(goal, pageable);
        return CommonResponse.success(result);
    }

    @GetMapping("/explore/decibel/{decibel}")
    public CommonResponse<PlaylistExploreListResponse> exploreByDecibel(
            @PathVariable String decibel,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PlaylistExploreListResponse result = playlistService.exploreByDecibel(decibel, pageable);
        return CommonResponse.success(result);
    }
}
