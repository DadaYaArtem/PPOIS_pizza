
package org.pizzeria.exception;

/**
 * Базовое исключение для аутентификации
 */
public class AuthenticationException extends PizzeriaException {
    
    public AuthenticationException(String message) {
        super(message, "AUTH_ERROR");
    }
    
    public AuthenticationException(String message, Throwable cause) {
        super(message, "AUTH_ERROR", cause);
    }
}