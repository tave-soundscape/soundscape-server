package com.soundscape.artist.controller;

import com.soundscape.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Artist", description = "아티스트 API")
public interface ArtistControllerDoc {

    @Operation(summary = "전체 아티스트 목록 조회", description = "선호 아티스트 선택에 필요한 전체 아티스트 조회 기능")
    CommonResponse getAllArtists();
}
