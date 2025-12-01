package com.soundscape.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private Result result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;
    private String message;
    private T data;

    public static <T> CommonResponse<T> success(T data, String message) {
        return CommonResponse.<T>builder()
                .result(Result.SUCCESS)
                .data(data)
                .message(message)
                .build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(data, null);
    }

    public static CommonResponse fail(String message, String errorCode) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(message)
                .errorCode(errorCode)
                .build();
    }

    public static CommonResponse fail(ErrorCode errorCode) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(errorCode.getErrorMsg())
                .errorCode(errorCode.name())
                .build();
    }

    public enum Result {
        SUCCESS, FAIL
    }
}
