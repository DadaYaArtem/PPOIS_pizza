package org.pizzeria.exception;

/**
 * Исключение при попытке изменить заказ, который нельзя редактировать
 */
public class OrderModificationException extends OrderException {
    
    public OrderModificationException(String message) {
        super(message);
    }
}