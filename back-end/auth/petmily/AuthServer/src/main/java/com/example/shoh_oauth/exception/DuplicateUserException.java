package com.example.shoh_oauth.exception;

import org.springframework.validation.FieldError;

import java.util.List;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException() {
    }

    public DuplicateUserException(String message) {
        super(message);
    }

}
