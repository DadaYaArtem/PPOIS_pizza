package org.pizzeria.domain.delivery;

import org.pizzeria.domain.common.Address;
import org.pizzeria.domain.common.DeliveryStatus;
import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.order.Order;
import org.pizzeria.domain.user.Courier;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс доставки заказа.
 * Связывает Order с Courier и отслеживает статус доставки.
 */
public class Delivery {
    private final String id;
    private final Order order;
    private final Address deliveryAddress;
    private Courier courier;
    private DeliveryStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime assignedAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;
    private Money deliveryFee;
    private int estimatedMinutes; // расчетное время доставки
    private String trackingCode;
    private String notes; // заметки курьера

    public Delivery(Order order, Address deliveryAddress, Money deliveryFee) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (deliveryAddress == null) {
            throw new IllegalArgumentException("Delivery address cannot be null");
        }
        if (deliveryFee == null) {
            throw new IllegalArgumentException("Delivery fee cannot be null");
        }

        this.id = UUID.randomUUID().toString();
        this.order = order;
        this.deliveryAddress = deliveryAddress;
        this.deliveryFee = deliveryFee;
        this.status = DeliveryStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.trackingCode = generateTrackingCode();
        this.notes = "";
        this.estimatedMinutes = 30; // по умолчанию 30 минут
    }

    // Конструктор для тестов с ID
    public Delivery(String id, Order order, Address deliveryAddress) {
        this.id = id;
        this.order = order;
        this.deliveryAddress = deliveryAddress;
        this.status = DeliveryStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.deliveryFee = Money.zero();
        this.trackingCode = generateTrackingCode();
        this.notes = "";
        this.estimatedMinutes = 30;
    }

    private String generateTrackingCode() {
        return "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;

        // Автоматически проставляем временные метки
        if (status == DeliveryStatus.ASSIGNED && assignedAt == null) {
            assignedAt = LocalDateTime.now();
        }
        if (status == DeliveryStatus.PICKED_UP && pickedUpAt == null) {
            pickedUpAt = LocalDateTime.now();
        }
        if (status == DeliveryStatus.DELIVERED && deliveredAt == null) {
            deliveredAt = LocalDateTime.now();
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public LocalDateTime getPickedUpAt() {
        return pickedUpAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public Money getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Money deliveryFee) {
        if (deliveryFee == null) {
            throw new IllegalArgumentException("Delivery fee cannot be null");
        }
        this.deliveryFee = deliveryFee;
    }

    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(int estimatedMinutes) {
        if (estimatedMinutes <= 0) {
            throw new IllegalArgumentException("Estimated time must be positive");
        }
        this.estimatedMinutes = estimatedMinutes;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }

    /**
     * Назначает курьера для доставки
     */
    public void assignCourier(Courier courier) {
        if (courier == null) {
            throw new IllegalArgumentException("Courier cannot be null");
        }
        if (!courier.isAvailable()) {
            throw new IllegalStateException("Courier is not available");
        }

        this.courier = courier;
        setStatus(DeliveryStatus.ASSIGNED);
    }

    /**
     * Отменяет назначение курьера
     */
    public void unassignCourier() {
        this.courier = null;
        setStatus(DeliveryStatus.PENDING);
        this.assignedAt = null;
    }

    /**
     * Проверяет, назначен ли курьер
     */
    public boolean isAssigned() {
        return courier != null;
    }

    /**
     * Проверяет, завершена ли доставка
     */
    public boolean isCompleted() {
        return status.isFinal();
    }

    /**
     * Вычисляет время доставки в минутах (если завершена)
     */
    public Integer getActualDeliveryTime() {
        if (pickedUpAt != null && deliveredAt != null) {
            return (int) java.time.Duration.between(pickedUpAt, deliveredAt).toMinutes();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(id, delivery.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Delivery{id='%s', order='%s', status=%s, courier='%s', tracking='%s'}",
            id, order.getId(), status, courier != null ? courier.getName() : "unassigned", trackingCode);
    }
}
