package com.example.oauth.exception;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException() {
    }

    public DuplicateUserException(String message) {
        super(message);
    }

}
