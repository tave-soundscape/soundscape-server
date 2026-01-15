package com.soundscape.playlist.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class PlaylistTaskStatusResponse {

    private final String taskId;
    private final String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final PlaylistResponse playlistInfo;

    public PlaylistTaskStatusResponse(String taskId, String status) {
        this.taskId = taskId;
        this.status = status;
        this.playlistInfo = null;
    }

    public PlaylistTaskStatusResponse(String taskId, String status, PlaylistResponse playlistInfo) {
        this.taskId = taskId;
        this.status = status;
        this.playlistInfo = playlistInfo;
    }
}