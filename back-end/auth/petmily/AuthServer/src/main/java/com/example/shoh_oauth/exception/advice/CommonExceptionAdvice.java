package com.example.shoh_oauth.exception.advice;

import com.example.shoh_oauth.exception.AuthenticationFailedException;
import com.example.shoh_oauth.exception.DataNotFoundException;
import com.example.shoh_oauth.exception.UnauthorizedException;
import com.example.shoh_oauth.exception.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@ControllerAdvice
@RestController
public class CommonExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ValidationException.class})
    public ErrorResponse validationExceptionHandler(ValidationException e) {
        log.error(e.getMessage(), e);
        List<FieldError> errors = e.getErrors();
        return new ErrorResponse(400, e.getMessage(), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, DataNotFoundException.class})
    public ErrorResponse badRequestHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(400, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {AuthenticationFailedException.class, UnauthorizedException.class})
    public ErrorResponse unauthorizedHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(401, e.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ErrorResponse methodNotAllowedHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(405, "지원하지 않는 API 입니다.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public ErrorResponse internalServerErrorHandler(Exception e) {
        String refreshCheck = e.getMessage();
        if (refreshCheck.substring(8, 15).equals("refresh")) {
            return new ErrorResponse(4005, "refreshToken이 만료 되었습니다.");
        }
        if (refreshCheck.substring(0,6).equals("Cannot")) {
            return new ErrorResponse(4004, "refreshToken이 없습니다");
        }

        log.error(e.getMessage(), e);
        return new ErrorResponse(500, e.getMessage());
    }

    @Getter
    @NoArgsConstructor
    public static class ErrorResponse {
        private int code;
        private String message;
        private List<FieldError> errors;

        public ErrorResponse(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public ErrorResponse(int code, String message, List<FieldError> errors) {
            this.code = code;
            this.message = message;
            this.errors = errors;
        }
    }
}
