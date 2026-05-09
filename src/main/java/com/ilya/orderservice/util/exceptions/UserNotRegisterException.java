package com.ilya.orderservice.util.exceptions;

public class UserNotRegisterException extends RuntimeException {
    public UserNotRegisterException(String message) {
        super(message);
    }
}
