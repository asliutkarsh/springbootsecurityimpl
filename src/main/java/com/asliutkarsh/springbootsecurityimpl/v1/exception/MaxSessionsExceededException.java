package com.asliutkarsh.springbootsecurityimpl.v1.exception;

public class MaxSessionsExceededException extends RuntimeException {
    public MaxSessionsExceededException(String message) {
        super(message);
    }
}
