package com.example.shoh_oauth.exception;



public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {

    }

    public DataNotFoundException(String message) {
        super(message);
    }
}