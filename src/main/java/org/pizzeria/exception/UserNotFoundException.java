package com.ppois.pizzeria.exception;

/**
 * Исключение когда пользователь не найден
 */
public class UserNotFoundException extends PizzeriaException {
    
    private final String userId;
    
    public UserNotFoundException(String userId) {
        super("User not found: " + userId, "USER_ERROR");
        this.userId = userId;
    }
    
    public String getUserId() {
        return userId;
    }
}