package com.github.gayanch.library.error;

public class AppException extends RuntimeException {
    private final int code;

    public AppException(int code, String message) {
        super(message);
        this.code = code;
    }

    public AppException(int code, String message, Throwable thr) {
        super(message, thr);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
