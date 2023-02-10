package com.devpet.feed.exception;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException() {

    }

    public DuplicateUserException(String message) {
        super(message);
    }
}