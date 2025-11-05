package com.ppois.pizzeria.exception;

/**
 * Исключение когда ингредиент закончился на складе
 */
public class OutOfStockException extends PizzeriaException {
    
    private final String ingredientName;
    
    public OutOfStockException(String ingredientName) {
        super("Ingredient out of stock: " + ingredientName, "INVENTORY_ERROR");
        this.ingredientName = ingredientName;
    }
    
    public String getIngredientName() {
        return ingredientName;
    }
}