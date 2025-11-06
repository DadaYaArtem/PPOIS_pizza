package org.pizzeria.validator;

import org.pizzeria.domain.order.Order;
import org.pizzeria.domain.order.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Валидатор для заказов.
 * Проверяет корректность данных заказа перед обработкой.
 */
public class OrderValidator {

    /**
     * Валидирует заказ
     * @return список ошибок (пустой, если заказ валиден)
     */
    public static List<String> validate(Order order) {
        List<String> errors = new ArrayList<>();

        if (order == null) {
            errors.add("Order cannot be null");
            return errors;
        }

        // Проверяем клиента
        if (order.getCustomer() == null) {
            errors.add("Order must have a customer");
        }

        // Проверяем позиции
        if (order.isEmpty()) {
            errors.add("Order must have at least one item");
        }

        // Проверяем, что все позиции доступны
        for (OrderItem item : order.getItems()) {
            if (!item.getMenuItem().isAvailable()) {
                errors.add(String.format("Item '%s' is not available",
                    item.getMenuItem().getName()));
            }

            if (item.getQuantity() <= 0) {
                errors.add(String.format("Invalid quantity for item '%s'",
                    item.getMenuItem().getName()));
            }
        }

        // Проверяем суммы
        if (order.getTotal().isZero()) {
            errors.add("Order total cannot be zero");
        }

        if (order.getDiscount().isGreaterThan(order.getSubtotal())) {
            errors.add("Discount cannot exceed subtotal");
        }

        return errors;
    }

    /**
     * Проверяет, валиден ли заказ
     */
    public static boolean isValid(Order order) {
        return validate(order).isEmpty();
    }

    /**
     * Валидирует заказ и выбрасывает исключение при ошибках
     */
    public static void validateAndThrow(Order order) {
        List<String> errors = validate(order);
        if (!errors.isEmpty()) {
            throw new IllegalStateException("Order validation failed: " +
                String.join(", ", errors));
        }
    }
}
