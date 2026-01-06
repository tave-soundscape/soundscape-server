package com.soundscape.playlist.infra.spotify;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpotifyPlaylistInfo {
    private String playlistId;
    private String playlistUrl;
}