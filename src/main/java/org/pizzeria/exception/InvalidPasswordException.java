package org.pizzeria.exception;

/**
 * Исключение при использовании слабого пароля
 */
public class InvalidPasswordException extends AuthenticationException {
    
    public InvalidPasswordException(String message) {
        super(message);
    }
    
    public InvalidPasswordException() {
        super("Password does not meet security requirements");
    }
}