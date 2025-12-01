package com.soundscape.common.exception;

import com.soundscape.common.response.ErrorCode;
import lombok.Getter;

//BaseException, BaseException 을 확장한 Exception 은 운영 중 예상이 가능한 Exception
@Getter
public class BaseException extends RuntimeException {

    private ErrorCode errorCode;

    public BaseException() {
    }

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
