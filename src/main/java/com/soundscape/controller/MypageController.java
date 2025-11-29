package com.soundscape.controller;

import com.soundscape.dto.*;
import com.soundscape.service.MypageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/{userId}")
    public ResponseEntity<MypageResponse> getMypage(@PathVariable Long userId) {
        return ResponseEntity.ok(mypageService.getMypage(userId));
    }

    @PatchMapping("/{userId}/name")
    public ResponseEntity<MypageResponse> updateName(
            @PathVariable Long userId,
            @Valid @RequestBody NameUpdateRequest req) {
        var res = mypageService.updateName(userId, req.name());
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{userId}/spotify")
    public ResponseEntity<MypageResponse> updateSpotify(
            @PathVariable Long userId,
            @Valid @RequestBody SpotifyUpdateRequest req
    ) {
        var res = mypageService.updateSpotifyAccount(userId, req.authorizationCode(), req.redirectUri());
        return ResponseEntity.ok(res);
    }

    // 아티스트 업데이트
    @PatchMapping("/{userId}/artists")
    public ResponseEntity<MypageResponse> updateArtists(
            @PathVariable Long userId,
            @Valid @RequestBody FavoriteArtistsUpdateRequest req) {
        var res = mypageService.updateArtists(userId, req);
        return ResponseEntity.ok(res);
    }

    // 장르 업데이트
    @PatchMapping("/{userId}/genres")
    public ResponseEntity<MypageResponse> updateGenres(
            @PathVariable Long userId,
            @Valid @RequestBody FavoriteGenresUpdateRequest req) {
        var res = mypageService.updateGenres(userId, req);
        return ResponseEntity.ok(res);
    }
}