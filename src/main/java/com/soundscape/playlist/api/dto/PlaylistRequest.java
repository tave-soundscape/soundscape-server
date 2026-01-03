package com.soundscape.playlist.api.dto;

import com.soundscape.playlist.service.PlaylistCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "플레이리스트 생성 요청 DTO")
@Getter
public class PlaylistRequest {
    @Schema(description = "사용자 위치 정보, cafe, library, park, gym ...", example = "gym")
    private String location;
    @Schema(description = "데시벨 정보, 45.3, 60.2, 86.0 ...", example = "49.7")
    private Float decibel;
    @Schema(description = "사용자 목표 정보, focus, relax, anger ...", example = "focus")
    private String goal;

    public PlaylistCommand toCommand() {
        String decibelCategory;
        if (decibel <= 50) decibelCategory = "quiet";
        else if (decibel <= 70) decibelCategory = "moderate";
        else decibelCategory = "loud";

        return new PlaylistCommand(location, decibelCategory, goal);
    }
}
