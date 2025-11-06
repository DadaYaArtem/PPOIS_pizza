package org.pizzeria.observer;

import org.pizzeria.domain.order.Order;

/**
 * Интерфейс наблюдателя для заказов.
 * Паттерн Observer позволяет объектам подписываться на события.
 */
public interface OrderObserver {
    /**
     * Вызывается при изменении статуса заказа
     */
    void onOrderStatusChanged(Order order);

    /**
     * Вызывается при создании заказа
     */
    void onOrderCreated(Order order);

    /**
     * Вызывается при отмене заказа
     */
    void onOrderCancelled(Order order);

    /**
     * Вызывается при завершении заказа
     */
    void onOrderCompleted(Order order);
}
