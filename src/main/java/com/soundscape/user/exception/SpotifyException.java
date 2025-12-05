package com.soundscape.user.exception;

import com.soundscape.common.exception.BaseException;
import com.soundscape.common.response.ErrorCode;

public class SpotifyException extends BaseException {

    public SpotifyException() {
        super(ErrorCode.SPOTIFY_API_ERROR);
    }

    public SpotifyException(String message) {
        super(message, ErrorCode.SPOTIFY_API_ERROR);
    }

    public SpotifyException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
