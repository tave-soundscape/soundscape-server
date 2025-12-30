package com.soundscape.mypage.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.mypage.api.dto.FavArtistsUpdateRequestDto;
import com.soundscape.mypage.api.dto.FavGenresUpdateRequestDto;
import com.soundscape.mypage.api.dto.NameUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Mypage", description = "마이페이지 관련 API")
public interface MypageDoc {
    @Operation(summary = "이름 수정 API", description = "사용자의 이름을 수정합니다.")
    CommonResponse updateName(@RequestBody NameUpdateRequestDto dto);
    @Operation(summary = "선호 아티스트 수정 API", description = "사용자의 선호 아티스트를 수정합니다.")
    CommonResponse updateFavArtists(@RequestBody FavArtistsUpdateRequestDto dto);
    @Operation(summary = "선호 장르 수정 API", description = "사용자의 선호 장르를 수정합니다.")
    CommonResponse updateFavGenres(@RequestBody FavGenresUpdateRequestDto dto);
}
