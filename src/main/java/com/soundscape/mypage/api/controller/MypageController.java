package com.soundscape.mypage.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.mypage.api.dto.FavArtistsUpdateRequestDto;
import com.soundscape.mypage.api.dto.NameUpdateRequestDto;
import com.soundscape.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @PatchMapping("/update/name")
    public CommonResponse updateName(@RequestParam Long userId, @RequestBody NameUpdateRequestDto dto) {
        mypageService.updateName(userId, dto);
        return CommonResponse.success("성공");
    }

    @PatchMapping("/update/fav_artists")
    public CommonResponse updateFavArtists(@RequestParam Long userId, @RequestBody FavArtistsUpdateRequestDto dto) {
        System.out.println(">>> updateFavArtists called");
        mypageService.updateFavArtists(userId, dto);
        return CommonResponse.success("성공");
    }
}
