package com.soundscape.mypage.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.mypage.api.dto.FavArtistsUpdateRequestDto;
import com.soundscape.mypage.api.dto.FavGenresUpdateRequestDto;
import com.soundscape.mypage.api.dto.NameUpdateRequestDto;
import com.soundscape.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @PatchMapping("/name")
    public CommonResponse updateName(@RequestParam Long userId, @RequestBody NameUpdateRequestDto dto) {
        mypageService.updateName(userId, dto);
        return CommonResponse.success("성공");
    }

    @PatchMapping("/fav_artists")
    public CommonResponse updateFavArtists(@RequestParam Long userId, @RequestBody FavArtistsUpdateRequestDto dto) {
        mypageService.updateFavArtists(userId, dto);
        return CommonResponse.success("성공");
    }

    @PatchMapping("/fav_genres")
    public CommonResponse updateFavGenres(@RequestParam Long userId, @RequestBody FavGenresUpdateRequestDto dto) {
        mypageService.updateFavGenres(userId, dto);
        return CommonResponse.success("성공");
    }
}
