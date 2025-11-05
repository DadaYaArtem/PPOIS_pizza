package com.ppois.pizzeria.domain.order;

import com.ppois.pizzeria.domain.common.Money;
import com.ppois.pizzeria.domain.common.OrderStatus;
import com.ppois.pizzeria.domain.menu.MenuItem;
import com.ppois.pizzeria.domain.user.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Заказ клиента.
 * Центральная доменная модель системы.
 * Содержит много ассоциаций: Customer, OrderItem, Money, OrderStatus.
 */
public class Order {
    private final String id;
    private final Customer customer;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime completedAt;
    private Money subtotal;
    private Money discount;
    private Money deliveryFee;
    private Money total;
    private String notes; // особые пожелания клиента

    public Order(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.DRAFT;
        this.createdAt = LocalDateTime.now();
        this.subtotal = Money.zero();
        this.discount = Money.zero();
        this.deliveryFee = Money.zero();
        this.total = Money.zero();
        this.notes = "";
    }

    public Order(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.DRAFT;
        this.createdAt = LocalDateTime.now();
        this.subtotal = Money.zero();
        this.discount = Money.zero();
        this.deliveryFee = Money.zero();
        this.total = Money.zero();
        this.notes = "";
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
        
        if (status == OrderStatus.CONFIRMED && confirmedAt == null) {
            confirmedAt = LocalDateTime.now();
        }
        if (status == OrderStatus.COMPLETED && completedAt == null) {
            completedAt = LocalDateTime.now();
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }

    // Управление позициями заказа
    public void addItem(MenuItem menuItem, int quantity) {
        if (!status.isEditable()) {
            throw new IllegalStateException("Cannot modify order in status: " + status);
        }
        
        // Проверяем, есть ли уже такая позиция
        for (OrderItem item : items) {
            if (item.getMenuItem().getId().equals(menuItem.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                recalculateTotal();
                return;
            }
        }
        
        // Добавляем новую позицию
        items.add(new OrderItem(menuItem, quantity));
        recalculateTotal();
    }

    public void removeItem(MenuItem menuItem) {
        if (!status.isEditable()) {
            throw new IllegalStateException("Cannot modify order in status: " + status);
        }
        
        items.removeIf(item -> item.getMenuItem().getId().equals(menuItem.getId()));
        recalculateTotal();
    }

    public void clearItems() {
        if (!status.isEditable()) {
            throw new IllegalStateException("Cannot modify order in status: " + status);
        }
        items.clear();
        recalculateTotal();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getItemCount() {
        return items.stream().mapToInt(OrderItem::getQuantity).sum();
    }

    // Управление ценами
    public Money getSubtotal() {
        return subtotal;
    }

    public Money getDiscount() {
        return discount;
    }

    public void setDiscount(Money discount) {
        if (discount == null) {
            throw new IllegalArgumentException("Discount cannot be null");
        }
        this.discount = discount;
        recalculateTotal();
    }

    public Money getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Money deliveryFee) {
        if (deliveryFee == null) {
            throw new IllegalArgumentException("Delivery fee cannot be null");
        }
        this.deliveryFee = deliveryFee;
        recalculateTotal();
    }

    public Money getTotal() {
        return total;
    }

    /**
     * Пересчитывает общую стоимость заказа
     */
    private void recalculateTotal() {
        // Подытог = сумма всех позиций
        subtotal = items.stream()
            .map(OrderItem::getTotalPrice)
            .reduce(Money.zero(), Money::add);
        
        // Итого = подытог - скидка + доставка
        total = subtotal.subtract(discount).add(deliveryFee);
    }

    /**
     * Проверяет, может ли заказ быть отменен
     */
    public boolean isCancellable() {
        return status.isCancellable();
    }

    /**
     * Проверяет, завершен ли заказ
     */
    public boolean isCompleted() {
        return status.isFinal();
    }

    @Override
    public String toString() {
        return String.format("Order{id='%s', customer='%s', status=%s, total=%s, items=%d}", 
            id, customer.getName(), status, total, items.size());
    }
}