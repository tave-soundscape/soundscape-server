package com.soundscape.playlist.api.controller;

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
    public PlaylistResponse generatePlaylist(@RequestBody PlaylistRequest request) {
        Long userId = 1L; // TODO: 나중에 인증 기능 구현되면 수정하기, 현재는 1고정
        return playlistService.generatePlaylist(userId);
    }

    @GetMapping
    public SimplePlaylistsResponse getUserPlaylists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Long userId = 1L; // TODO: 나중에 인증 기능 구현되면 수정하기, 현재는 1고정
        return playlistService.getUserPlaylists(userId, page, size);
    }
}
