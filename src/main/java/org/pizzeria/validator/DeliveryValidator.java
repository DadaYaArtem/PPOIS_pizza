package org.pizzeria.validator;

import org.pizzeria.domain.delivery.Delivery;

import java.util.ArrayList;
import java.util.List;

/**
 * Валидатор для доставок.
 * Проверяет корректность данных доставки.
 */
public class DeliveryValidator {

    /**
     * Валидирует доставку
     * @return список ошибок (пустой, если доставка валидна)
     */
    public static List<String> validate(Delivery delivery) {
        List<String> errors = new ArrayList<>();

        if (delivery == null) {
            errors.add("Delivery cannot be null");
            return errors;
        }

        // Проверяем заказ
        if (delivery.getOrder() == null) {
            errors.add("Delivery must be associated with an order");
        }

        // Проверяем адрес доставки
        if (delivery.getDeliveryAddress() == null) {
            errors.add("Delivery address cannot be null");
        }

        // Проверяем стоимость доставки
        if (delivery.getDeliveryFee() == null) {
            errors.add("Delivery fee cannot be null");
        }

        // Проверяем расчетное время
        if (delivery.getEstimatedMinutes() <= 0) {
            errors.add("Estimated delivery time must be positive");
        }

        // Проверяем, что курьер доступен, если назначен
        if (delivery.getCourier() != null && !delivery.getCourier().isAvailable()) {
            errors.add("Assigned courier is not available");
        }

        return errors;
    }

    /**
     * Проверяет, валидна ли доставка
     */
    public static boolean isValid(Delivery delivery) {
        return validate(delivery).isEmpty();
    }

    /**
     * Валидирует доставку и выбрасывает исключение при ошибках
     */
    public static void validateAndThrow(Delivery delivery) {
        List<String> errors = validate(delivery);
        if (!errors.isEmpty()) {
            throw new IllegalStateException("Delivery validation failed: " +
                String.join(", ", errors));
        }
    }
}
