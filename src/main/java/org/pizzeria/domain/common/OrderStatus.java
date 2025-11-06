package org.pizzeria.domain.common;

/**
 * Статус заказа (для State Pattern)
 */
public enum OrderStatus {
    DRAFT("Draft", "Order is being created"),
    PENDING_PAYMENT("Pending Payment", "Waiting for payment"),
    PAID("Paid", "Payment confirmed"),
    CONFIRMED("Confirmed", "Order confirmed, sent to kitchen"),
    PREPARING("Preparing", "Being prepared in kitchen"),
    READY("Ready", "Ready for pickup/delivery"),
    OUT_FOR_DELIVERY("Out for Delivery", "Courier is on the way"),
    DELIVERED("Delivered", "Successfully delivered"),
    COMPLETED("Completed", "Order completed"),
    CANCELLED("Cancelled", "Order cancelled");

    private final String displayName;
    private final String description;

    OrderStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Проверяет, можно ли редактировать заказ в этом статусе
     */
    public boolean isEditable() {
        return this == DRAFT;
    }

    /**
     * Проверяет, можно ли отменить заказ в этом статусе
     */
    public boolean isCancellable() {
        return this == DRAFT || this == PENDING_PAYMENT || 
               this == PAID || this == CONFIRMED;
    }

    /**
     * Проверяет, является ли статус финальным
     */
    public boolean isFinal() {
        return this == DELIVERED || this == COMPLETED || this == CANCELLED;
    }

    @Override
    public String toString() {
        return displayName;
    }
}