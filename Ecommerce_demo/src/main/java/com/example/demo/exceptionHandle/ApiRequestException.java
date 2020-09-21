package com.example.demo.exceptionHandle;

public class ApiRequestException extends RuntimeException {//throw new This class with message
    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
