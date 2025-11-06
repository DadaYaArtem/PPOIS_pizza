package org.pizzeria.strategy;

import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.order.Order;

/**
 * Интерфейс стратегии для расчета скидок.
 * Паттерн Strategy позволяет выбирать алгоритм во время выполнения.
 */
public interface DiscountStrategy {
    /**
     * Вычисляет скидку для заказа
     */
    Money calculateDiscount(Order order);

    /**
     * Возвращает описание стратегии скидки
     */
    String getDescription();

    /**
     * Проверяет, применима ли скидка к заказу
     */
    boolean isApplicable(Order order);
}
