package org.pizzeria.exception;

/**
 * Базовое исключение для операций с платежами
 */
public class PaymentException extends PizzeriaException {
    
    public PaymentException(String message) {
        super(message, "PAYMENT_ERROR");
    }
    
    public PaymentException(String message, Throwable cause) {
        super(message, "PAYMENT_ERROR", cause);
    }
}