package com.zerobase.cms.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({
            CustomException.class
    })
    public ResponseEntity<ExceptionResponse> customRequestException(final CustomException c) {
        log.warn("api Exception : {}", c.getErrorCode());
        return ResponseEntity.badRequest().body(new ExceptionResponse(c.getMessage(), c.getErrorCode()));
    }

    @ToString
    @Getter
    @AllArgsConstructor
    public static class ExceptionResponse{
        private String message;
        private ErrorCode errorCode;
    }
}
