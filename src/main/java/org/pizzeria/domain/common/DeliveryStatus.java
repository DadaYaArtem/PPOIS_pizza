package org.pizzeria.domain.common;

/**
 * Статус доставки
 */
public enum DeliveryStatus {
    PENDING("Pending", "Delivery not yet assigned"),
    ASSIGNED("Assigned", "Assigned to courier"),
    PICKED_UP("Picked Up", "Courier picked up the order"),
    IN_TRANSIT("In Transit", "On the way to customer"),
    NEARBY("Nearby", "Courier is nearby (5 min)"),
    ARRIVED("Arrived", "Courier arrived at destination"),
    DELIVERED("Delivered", "Successfully delivered"),
    FAILED("Failed", "Delivery failed");

    private final String displayName;
    private final String description;

    DeliveryStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFinal() {
        return this == DELIVERED || this == FAILED;
    }

    @Override
    public String toString() {
        return displayName;
    }
}