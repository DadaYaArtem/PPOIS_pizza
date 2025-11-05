package com.ppois.pizzeria.exception;

/**
 * Исключение при провале валидации
 */
public class ValidationException extends PizzeriaException {
    
    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR");
    }
    
    public ValidationException(String fieldName, String reason) {
        super(String.format("Validation failed for %s: %s", fieldName, reason), "VALIDATION_ERROR");
    }
}