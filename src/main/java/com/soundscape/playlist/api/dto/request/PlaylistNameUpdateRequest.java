package com.soundscape.playlist.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaylistNameUpdateRequest {

    private final String newPlaylistName;
}
