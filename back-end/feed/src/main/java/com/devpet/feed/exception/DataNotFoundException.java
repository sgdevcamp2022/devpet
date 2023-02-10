package com.devpet.feed.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {

    }

    public DataNotFoundException(String message) {
        super(message);
    }
}