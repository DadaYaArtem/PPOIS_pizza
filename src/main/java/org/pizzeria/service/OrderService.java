package org.pizzeria.service;

import org.pizzeria.domain.common.OrderStatus;
import org.pizzeria.domain.menu.MenuItem;
import org.pizzeria.domain.order.Order;
import org.pizzeria.domain.user.Customer;
import org.pizzeria.observer.OrderNotifier;
import org.pizzeria.strategy.DiscountStrategy;
import org.pizzeria.validator.OrderValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления заказами.
 * Координирует создание, обновление и обработку заказов.
 */
public class OrderService {
    private final List<Order> orders;
    private final OrderNotifier notifier;

    public OrderService(OrderNotifier notifier) {
        this.orders = new ArrayList<>();
        this.notifier = notifier != null ? notifier : new OrderNotifier();
    }

    public OrderService() {
        this(new OrderNotifier());
    }

    /**
     * Создает новый заказ
     */
    public Order createOrder(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        Order order = new Order(customer);
        orders.add(order);
        notifier.notifyOrderCreated(order);

        return order;
    }

    /**
     * Добавляет позицию в заказ
     */
    public void addItemToOrder(Order order, MenuItem menuItem, int quantity) {
        if (!orders.contains(order)) {
            throw new IllegalArgumentException("Order not found");
        }

        order.addItem(menuItem, quantity);
    }

    /**
     * Применяет скидку к заказу
     */
    public void applyDiscount(Order order, DiscountStrategy discountStrategy) {
        if (!orders.contains(order)) {
            throw new IllegalArgumentException("Order not found");
        }

        if (discountStrategy.isApplicable(order)) {
            order.setDiscount(discountStrategy.calculateDiscount(order));
        }
    }

    /**
     * Подтверждает заказ
     */
    public void confirmOrder(Order order) {
        if (!orders.contains(order)) {
            throw new IllegalArgumentException("Order not found");
        }

        // Валидируем заказ
        OrderValidator.validateAndThrow(order);

        order.setStatus(OrderStatus.CONFIRMED);
        notifier.notifyOrderStatusChanged(order);
    }

    /**
     * Отменяет заказ
     */
    public void cancelOrder(Order order) {
        if (!orders.contains(order)) {
            throw new IllegalArgumentException("Order not found");
        }

        if (!order.isCancellable()) {
            throw new IllegalStateException("Order cannot be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        notifier.notifyOrderCancelled(order);
    }

    /**
     * Завершает заказ
     */
    public void completeOrder(Order order) {
        if (!orders.contains(order)) {
            throw new IllegalArgumentException("Order not found");
        }

        order.setStatus(OrderStatus.COMPLETED);
        notifier.notifyOrderCompleted(order);
    }

    /**
     * Обновляет статус заказа
     */
    public void updateOrderStatus(Order order, OrderStatus newStatus) {
        if (!orders.contains(order)) {
            throw new IllegalArgumentException("Order not found");
        }

        order.setStatus(newStatus);
        notifier.notifyOrderStatusChanged(order);
    }

    /**
     * Находит заказ по ID
     */
    public Optional<Order> findOrderById(String orderId) {
        return orders.stream()
            .filter(o -> o.getId().equals(orderId))
            .findFirst();
    }

    /**
     * Находит все заказы клиента
     */
    public List<Order> findOrdersByCustomer(Customer customer) {
        return orders.stream()
            .filter(o -> o.getCustomer().equals(customer))
            .toList();
    }

    /**
     * Находит заказы по статусу
     */
    public List<Order> findOrdersByStatus(OrderStatus status) {
        return orders.stream()
            .filter(o -> o.getStatus() == status)
            .toList();
    }

    /**
     * Возвращает все заказы
     */
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Возвращает notifier для добавления наблюдателей
     */
    public OrderNotifier getNotifier() {
        return notifier;
    }

    /**
     * Удаляет заказ (только для тестов)
     */
    void removeOrder(Order order) {
        orders.remove(order);
    }
}
