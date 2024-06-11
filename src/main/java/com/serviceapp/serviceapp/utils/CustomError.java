package com.serviceapp.serviceapp.utils;

public class CustomError extends RuntimeException {
    private final int statusCode;

    public CustomError(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
