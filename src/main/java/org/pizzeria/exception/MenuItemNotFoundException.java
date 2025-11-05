package com.ppois.pizzeria.exception;

/**
 * Исключение когда позиция меню не найдена
 */
public class MenuItemNotFoundException extends PizzeriaException {
    
    private final String itemId;
    
    public MenuItemNotFoundException(String itemId) {
        super("Menu item not found: " + itemId, "MENU_ERROR");
        this.itemId = itemId;
    }
    
    public String getItemId() {
        return itemId;
    }
}