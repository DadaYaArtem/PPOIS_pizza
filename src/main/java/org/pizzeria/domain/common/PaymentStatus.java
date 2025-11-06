package org.pizzeria.domain.common;

/**
 * Статус платежа
 */
public enum PaymentStatus {
    PENDING("Pending", "Payment initiated"),
    PROCESSING("Processing", "Payment being processed"),
    COMPLETED("Completed", "Payment successful"),
    FAILED("Failed", "Payment failed"),
    REFUNDED("Refunded", "Payment refunded"),
    CANCELLED("Cancelled", "Payment cancelled");

    private final String displayName;
    private final String description;

    PaymentStatus(String displayName, String description) {
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
        return this == COMPLETED || this == FAILED || 
               this == REFUNDED || this == CANCELLED;
    }

    public boolean isSuccessful() {
        return this == COMPLETED;
    }

    @Override
    public String toString() {
        return displayName;
    }
}