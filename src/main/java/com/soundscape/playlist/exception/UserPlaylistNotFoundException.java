package com.soundscape.playlist.exception;

import com.soundscape.common.exception.EntityNotFoundException;
import com.soundscape.common.response.ErrorCode;

public class UserPlaylistNotFoundException extends EntityNotFoundException {

    public UserPlaylistNotFoundException() {
        super(ErrorCode.USER_PLAYLIST_NOT_FOUND);
    }

    public UserPlaylistNotFoundException(String message) {
        super(message, ErrorCode.USER_PLAYLIST_NOT_FOUND);
    }
}
