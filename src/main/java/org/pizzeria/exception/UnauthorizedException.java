package org.pizzeria.exception;

/**
 * Исключение при попытке доступа без авторизации
 */
public class UnauthorizedException extends AuthenticationException {
    
    public UnauthorizedException() {
        super("Unauthorized access");
    }
    
    public UnauthorizedException(String message) {
        super(message);
    }
}