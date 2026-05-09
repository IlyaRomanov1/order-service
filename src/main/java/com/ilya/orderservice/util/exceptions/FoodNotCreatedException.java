package com.ilya.orderservice.util.exceptions;

public class FoodNotCreatedException extends RuntimeException {
    public FoodNotCreatedException(String message) {
        super(message);
    }
}
