package com.soundscape.common.response;

import com.soundscape.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {

    // http status: 500, result: FAIL
    // 시스템 예외 상황
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonResponse onException(Exception e) {
        log.error("[GlobalException] cause = {}, message = {}", e.getClass(), e.getMessage(), e);
        return CommonResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR);
    }

     // http status: 200, result: FAIL
     // 시스템은 이슈 없고, 서비스 로직 처리에서 에러가 발생
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BaseException.class)
    public CommonResponse onBaseException(BaseException e) {
        log.warn("[BaseException] cause = {}, message = {}", e.getErrorCode(), e.getMessage());
        return CommonResponse.fail(e.getErrorCode());
    }

    // http status: 400, result: FAIL
    // request parameter 에러 1
    // @Valid 유효성 검증 실패 (@NotNull 필드 누락 등)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError firstFieldError = bindingResult.getFieldError();

        String errorMessage = (firstFieldError != null)
                ? firstFieldError.getDefaultMessage()
                : ErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg();

        log.warn("[MethodArgumentNotValidException] field = {}, message = {}",
                (firstFieldError != null ? firstFieldError.getField() : "unknown error"),
                errorMessage);

        return CommonResponse.fail(errorMessage, ErrorCode.COMMON_INVALID_PARAMETER.name());
    }

     // http status: 400, result: FAIL
     // request parameter 에러 2
     // JSON 파싱 실패 (숫자가 와야 할 필드에 문자열 입력, 콤마 누락 등)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public CommonResponse httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("[HttpMessageNotReadableException] message = {}", e.getMessage());
        return CommonResponse.fail("입력 형식이 올바르지 않습니다.", ErrorCode.COMMON_INVALID_PARAMETER.name());
    }

    // http status: 404, result: FAIL
    // 존재하지 않는 경로 요청 처리
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NoResourceFoundException.class})
    public CommonResponse onNoResourceFoundException(Exception e) {
        log.warn("[404 Not Found] not found path requested. message = {}", e.getMessage());
        return CommonResponse.fail("존재하지 않는 경로입니다.", "NOT_FOUND");
    }
}
