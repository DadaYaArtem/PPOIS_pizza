package org.pizzeria.domain.order;

import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.menu.MenuItem;

import java.util.Objects;

/**
 * Позиция в заказе (товар + количество).
 * Ассоциация с MenuItem.
 */
public class OrderItem {
    private final MenuItem menuItem;
    private int quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        if (menuItem == null) {
            throw new IllegalArgumentException("Menu item cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        if (quantity <= 1) {
            throw new IllegalStateException("Cannot decrement quantity below 1");
        }
        this.quantity--;
    }

    /**
     * Возвращает общую стоимость позиции (цена * количество)
     */
    public Money getTotalPrice() {
        return menuItem.getPrice().multiply(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(menuItem.getId(), orderItem.menuItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItem.getId());
    }

    @Override
    public String toString() {
        return String.format("%s x%d - %s", 
            menuItem.getName(), quantity, getTotalPrice());
    }
}