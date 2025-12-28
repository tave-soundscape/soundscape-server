package com.soundscape.mypage.api.controller;

import com.soundscape.common.auth.context.UserContextHolder;
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
    public CommonResponse updateName(@RequestBody NameUpdateRequestDto dto) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        mypageService.updateName(userId, dto);
        return CommonResponse.success("성공");
    }

    @PatchMapping("/fav_artists")
    public CommonResponse updateFavArtists(@RequestBody FavArtistsUpdateRequestDto dto) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        mypageService.updateFavArtists(userId, dto);
        return CommonResponse.success("성공");
    }

    @PatchMapping("/fav_genres")
    public CommonResponse updateFavGenres(@RequestBody FavGenresUpdateRequestDto dto) {
        Long userId = Long.valueOf(UserContextHolder.getUserContext());
        mypageService.updateFavGenres(userId, dto);
        return CommonResponse.success("성공");
    }
}
