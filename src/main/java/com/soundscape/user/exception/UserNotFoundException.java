package com.soundscape.user.exception;

import com.soundscape.common.exception.EntityNotFoundException;
import com.soundscape.common.response.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND);
    }
}
