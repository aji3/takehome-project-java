package com.kazuya.app.exception;

public class StandardException extends RuntimeException {
    private int statusCode;
    public StandardException(int statusCode, String message, Exception cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
