package com.soundscape.playlist.api.dto.response;

import com.soundscape.playlist.domain.UserPlaylist;
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

        public SimpleInfo(UserPlaylist userPlaylist) {
            playlistId = userPlaylist.getPlaylist().getId();
            playlistName = userPlaylist.getCustomPlaylistName();
        }
    }
}
