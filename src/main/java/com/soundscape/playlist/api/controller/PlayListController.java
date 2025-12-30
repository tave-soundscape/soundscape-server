package com.soundscape.playlist.api.controller;

import com.soundscape.common.auth.context.UserContextHolder;
import com.soundscape.common.response.CommonResponse;
import com.soundscape.playlist.api.dto.PlaylistNameUpdateRequest;
import com.soundscape.playlist.api.dto.PlaylistRequest;
import com.soundscape.playlist.api.dto.PlaylistResponse;
import com.soundscape.playlist.api.dto.SimplePlaylistsResponse;
import com.soundscape.playlist.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/playlists")
@RequiredArgsConstructor
public class PlayListController implements PlaylistControllerDoc {

    private final PlaylistService playlistService;

    @PostMapping
    public CommonResponse<PlaylistResponse> generatePlaylist(@RequestBody PlaylistRequest request) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        PlaylistResponse result = playlistService.generatePlaylist(userId);
        return CommonResponse.success(result);
    }

    @PatchMapping("/{playlistId}")
    public CommonResponse savePlaylist(@PathVariable Long playlistId, @RequestBody PlaylistNameUpdateRequest newPlaylistName) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        playlistService.savePlaylist(playlistId, userId, newPlaylistName.getNewPlaylistName());
        return CommonResponse.success("playlist ID: " + playlistId, "플레이리스트가 저장되었습니다.");
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
        PlaylistResponse result = playlistService.getPlaylistDetails(playlistId);
        return CommonResponse.success(result);
    }
}
