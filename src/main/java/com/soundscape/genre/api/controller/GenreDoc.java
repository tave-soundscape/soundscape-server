package com.soundscape.genre.api.controller;

import com.soundscape.common.response.CommonResponse;
import com.soundscape.genre.api.dto.GenreDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Genre", description = "장르 조회 API")
public interface GenreDoc {
    @Operation(summary = "전체 장르 조회 API", description = "사용자가 선호 장르를 선택하기 전 전체 장르를 조회합니다.")
    CommonResponse<List<GenreDto>> getAllGenres();
}
