package com.soundscape.common.auth.exception;

import com.soundscape.common.exception.BaseException;
import com.soundscape.common.response.ErrorCode;

public class CustomJwtException extends BaseException {

    public CustomJwtException() {
        super(ErrorCode.JWT_INVALID_TOKEN);
    }

    public CustomJwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
