package com.asliutkarsh.springbootsecurityimpl.v1.exception;

public class DuplicationEntryException extends RuntimeException {
    public DuplicationEntryException(String message) {
        super(message);
    }
}
