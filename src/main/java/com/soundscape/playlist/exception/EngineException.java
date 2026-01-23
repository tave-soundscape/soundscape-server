package com.soundscape.playlist.exception;

import com.soundscape.common.exception.BaseException;
import com.soundscape.common.response.ErrorCode;

public class EngineException extends BaseException {

    public EngineException(String message) {
        super(message, ErrorCode.ENGINE_ERROR);
    }
}