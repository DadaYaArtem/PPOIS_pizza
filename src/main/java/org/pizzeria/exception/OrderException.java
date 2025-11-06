package org.pizzeria.exception;

/**
 * Базовое исключение для операций с заказами
 */
public class OrderException extends PizzeriaException {

    public OrderException(String message) {
        super(message, "ORDER_ERROR");
    }

    public OrderException(String message, Throwable cause) {
        super(message, "ORDER_ERROR", cause);
    }
}
