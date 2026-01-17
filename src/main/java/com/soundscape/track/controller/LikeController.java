package com.soundscape.track.controller;

import com.soundscape.common.auth.context.UserContextHolder;
import com.soundscape.common.response.CommonResponse;
import com.soundscape.playlist.api.dto.response.PlaylistResponse;
import com.soundscape.track.domain.LikesSummaryResponse;
import com.soundscape.track.domain.TrackLikeRequest;
import com.soundscape.track.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/tracks")
    public CommonResponse likeTrack(@RequestBody TrackLikeRequest request) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        likeService.likeTrack(userId, request.getTrackUri());
        return CommonResponse.success("좋아요에 추가되었습니다.");
    }

    @DeleteMapping("/tracks")
    public CommonResponse unlikeTrack(@RequestBody TrackLikeRequest request) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        likeService.unlikeTrack(userId, request.getTrackUri());
        return CommonResponse.success("좋아요가 취소되었습니다.");
    }

    @GetMapping("/summary")
    public CommonResponse<LikesSummaryResponse> getSummary() {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        LikesSummaryResponse result = likeService.getLikesSummary(userId);
        return CommonResponse.success(result);
    }

    @GetMapping
    public CommonResponse<PlaylistResponse> getLikesPlaylist() {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        PlaylistResponse result = likeService.getLikesPlaylist(userId);
        return CommonResponse.success(result);
    }
}
