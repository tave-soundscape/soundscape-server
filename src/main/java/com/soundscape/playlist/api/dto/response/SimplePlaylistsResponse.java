package com.soundscape.playlist.api.dto.response;

import com.soundscape.playlist.domain.Playlist;
import lombok.Getter;

import java.util.List;

@Getter
public class SimplePlaylistsResponse {

    private final List<SimpleInfo> playlists;
    private final boolean hasNext;

    public SimplePlaylistsResponse(List<SimpleInfo> playlists, boolean hasNext) {
        this.playlists = playlists;
        this.hasNext = hasNext;
    }

    @Getter
    public static class SimpleInfo {
        private final Long playlistId;
        private final String playlistName;

        public SimpleInfo(Playlist playlist) {
            playlistId = playlist.getId();
            playlistName = playlist.getPlaylistName();
        }
    }
}
