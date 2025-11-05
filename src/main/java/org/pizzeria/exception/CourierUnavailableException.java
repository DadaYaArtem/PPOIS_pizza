package com.ppois.pizzeria.exception;

/**
 * Исключение когда нет доступных курьеров
 */
public class CourierUnavailableException extends DeliveryException {
    
    public CourierUnavailableException() {
        super("No couriers available at the moment");
    }
    
    public CourierUnavailableException(String message) {
        super(message);
    }
}