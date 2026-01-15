package com.soundscape.playlist.exception;

import com.soundscape.common.exception.EntityNotFoundException;
import com.soundscape.common.response.ErrorCode;

public class TaskNotFoundException extends EntityNotFoundException {

    public TaskNotFoundException() {
        super(ErrorCode.PLAYLIST_GENERATION_TASK_NOT_FOUND);
    }

    public TaskNotFoundException(String message) {
        super(message, ErrorCode.PLAYLIST_GENERATION_TASK_NOT_FOUND);
    }
}