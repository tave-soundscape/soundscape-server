package com.soundscape.playlist.exception;

import com.soundscape.common.exception.BaseException;
import com.soundscape.common.response.ErrorCode;

public class SpotifyApiException extends BaseException {

    public SpotifyApiException(String message) {
        super(message, ErrorCode.SPOTIFY_API_ERROR);
    }
}
