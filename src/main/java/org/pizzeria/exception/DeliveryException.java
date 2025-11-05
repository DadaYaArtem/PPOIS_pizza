package com.ppois.pizzeria.exception;

/**
 * Базовое исключение для операций с доставкой
 */
public class DeliveryException extends PizzeriaException {
    
    public DeliveryException(String message) {
        super(message, "DELIVERY_ERROR");
    }
    
    public DeliveryException(String message, Throwable cause) {
        super(message, "DELIVERY_ERROR", cause);
    }
}