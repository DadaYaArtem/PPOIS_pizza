package org.pizzeria.exception;

/**
 * Исключение при работе с невалидным ингредиентом
 */
public class InvalidIngredientException extends PizzeriaException {
    
    public InvalidIngredientException(String message) {
        super(message, "INGREDIENT_ERROR");
    }
}