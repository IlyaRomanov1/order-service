package com.ilya.orderservice.util.exceptions;

public class FoodNotUpdatedException extends RuntimeException {
    public FoodNotUpdatedException(String message) {
        super(message);
    }
}
