package com.ppois.pizzeria.exception;

/**
 * Исключение при неверных учетных данных
 */
public class InvalidCredentialsException extends AuthenticationException {
    
    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
}