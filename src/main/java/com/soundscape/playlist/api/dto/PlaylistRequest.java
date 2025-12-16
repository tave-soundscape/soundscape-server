package com.soundscape.playlist.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "플레이리스트 생성 요청 DTO")
@Getter
public class PlaylistRequest {
    @Schema(description = "사용자 위치 정보, cafe, library, park, gym ...", example = "gym")
    private String location;
    @Schema(description = "데시벨 구간 정보, silent, quiet, moderate, loud ...", example = "loud")
    private String decibel;
    @Schema(description = "사용자 목표 정보, focus, relax, anger ...", example = "focus")
    private String goal;
}
