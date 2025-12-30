package com.soundscape.playlist.exception;

import com.soundscape.common.exception.EntityNotFoundException;
import com.soundscape.common.response.ErrorCode;

public class PlaylistNotFoundException extends EntityNotFoundException {

    public PlaylistNotFoundException() {
        super(ErrorCode.PLAYLIST_NOT_FOUND);
    }

    public PlaylistNotFoundException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND);
    }
}
