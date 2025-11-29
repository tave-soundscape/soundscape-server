package com.soundscape.dto.spotify;

import java.util.List;

public record SpotifyProfile(
        String id,
        String display_name,
        String email,
        List<ImageInfo> images
) {
    public record ImageInfo(String url, int height, int width) {}
}