
package com.ppois.pizzeria.exception;

/**
 * Исключение при попытке отменить заказ, который нельзя отменить
 */
public class OrderCancellationException extends OrderException {
    
    public OrderCancellationException(String message) {
        super(message);
    }
    
    public OrderCancellationException(String orderId, String currentStatus) {
        super(String.format("Cannot cancel order %s in status: %s", orderId, currentStatus));
    }
}