package org.pizzeria.exception;

/**
 * Исключение при ошибке обработки платежа
 */
public class PaymentProcessingException extends PaymentException {
    
    public PaymentProcessingException(String message) {
        super(message);
    }
    
    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}