package com.ppois.pizzeria.exception;

/**
 * Исключение когда заказ не найден в системе
 */
public class OrderNotFoundException extends OrderException {
    
    private final String orderId;
    
    public OrderNotFoundException(String orderId) {
        super("Order not found: " + orderId);
        this.orderId = orderId;
    }
    
    public String getOrderId() {
        return orderId;
    }
}