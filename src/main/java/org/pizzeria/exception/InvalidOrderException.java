package com.ppois.pizzeria.exception;

/**
 * Исключение при попытке создать или изменить невалидный заказ
 */
public class InvalidOrderException extends OrderException {
    
    public InvalidOrderException(String message) {
        super(message);
    }
    
    public InvalidOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}