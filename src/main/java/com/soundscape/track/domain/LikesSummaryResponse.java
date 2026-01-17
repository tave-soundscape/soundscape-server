package com.soundscape.track.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikesSummaryResponse {
    private String spotifyPlaylistId;
    private String playlistUrl;
    private int songCount; // likesCount 사용
}
