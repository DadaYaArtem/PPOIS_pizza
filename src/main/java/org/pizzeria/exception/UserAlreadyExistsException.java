package com.ppois.pizzeria.exception;

/**
 * Исключение когда пользователь уже существует
 */
public class UserAlreadyExistsException extends PizzeriaException {
    
    private final String email;
    
    public UserAlreadyExistsException(String email) {
        super("User already exists with email: " + email, "USER_ERROR");
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
}