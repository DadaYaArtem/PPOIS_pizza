package org.pizzeria.observer;

import org.pizzeria.domain.order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Уведомитель для заказов.
 * Управляет списком наблюдателей и рассылает уведомления.
 */
public class OrderNotifier {
    private final List<OrderObserver> observers;

    public OrderNotifier() {
        // CopyOnWriteArrayList для thread-safety
        this.observers = new CopyOnWriteArrayList<>();
    }

    /**
     * Добавляет наблюдателя
     */
    public void addObserver(OrderObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Удаляет наблюдателя
     */
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    /**
     * Удаляет всех наблюдателей
     */
    public void clearObservers() {
        observers.clear();
    }

    /**
     * Возвращает количество наблюдателей
     */
    public int getObserverCount() {
        return observers.size();
    }

    /**
     * Уведомляет об изменении статуса заказа
     */
    public void notifyOrderStatusChanged(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        for (OrderObserver observer : observers) {
            try {
                observer.onOrderStatusChanged(order);
            } catch (Exception e) {
                // Логируем ошибку, но продолжаем уведомлять других
                System.err.println("Error notifying observer: " + e.getMessage());
            }
        }
    }

    /**
     * Уведомляет о создании заказа
     */
    public void notifyOrderCreated(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        for (OrderObserver observer : observers) {
            try {
                observer.onOrderCreated(order);
            } catch (Exception e) {
                System.err.println("Error notifying observer: " + e.getMessage());
            }
        }
    }

    /**
     * Уведомляет об отмене заказа
     */
    public void notifyOrderCancelled(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        for (OrderObserver observer : observers) {
            try {
                observer.onOrderCancelled(order);
            } catch (Exception e) {
                System.err.println("Error notifying observer: " + e.getMessage());
            }
        }
    }

    /**
     * Уведомляет о завершении заказа
     */
    public void notifyOrderCompleted(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        for (OrderObserver observer : observers) {
            try {
                observer.onOrderCompleted(order);
            } catch (Exception e) {
                System.err.println("Error notifying observer: " + e.getMessage());
            }
        }
    }
}
