package com.asliutkarsh.springbootsecurityimpl.v1.exception;

public class WrongInputException extends RuntimeException{
    public WrongInputException(String message) {
        super(message);
    }

    public WrongInputException() {
        super("Invalid Username or Password");
    }
}