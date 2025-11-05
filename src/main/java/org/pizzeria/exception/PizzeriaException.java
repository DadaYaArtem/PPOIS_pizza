package com.ppois.pizzeria.exception;

/**
 * Базовое исключение для всей системы пиццерии.
 * Все остальные исключения наследуются от него.
 */
public class PizzeriaException extends Exception {
    
    private final String errorCode;
    
    public PizzeriaException(String message) {
        super(message);
        this.errorCode = "PIZZERIA_ERROR";
    }
    
    public PizzeriaException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public PizzeriaException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "PIZZERIA_ERROR";
    }
    
    public PizzeriaException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s", errorCode, getMessage());
    }
}