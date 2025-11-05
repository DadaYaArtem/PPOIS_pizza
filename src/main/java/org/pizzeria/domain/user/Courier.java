package com.ppois.pizzeria.domain.user;

import com.ppois.pizzeria.domain.common.Email;
import com.ppois.pizzeria.domain.common.Location;
import com.ppois.pizzeria.domain.common.Money;
import com.ppois.pizzeria.domain.common.PhoneNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Курьер для доставки заказов.
 * Отслеживает текущую локацию и активные доставки.
 */
public class Courier extends Employee {
    private Location currentLocation;
    private String vehicleType; // "Bike", "Car", "Scooter"
    private final List<String> activeDeliveryIds;
    private int maxActiveDeliveries;
    private int completedDeliveriesCount;

    public Courier(String name, Email email, PhoneNumber phoneNumber,
                   String passwordHash, Money salary, String vehicleType) {
        super(name, email, phoneNumber, passwordHash, salary);
        this.vehicleType = vehicleType != null ? vehicleType : "Bike";
        this.activeDeliveryIds = new ArrayList<>();
        this.maxActiveDeliveries = 2;
        this.completedDeliveriesCount = 0;
    }

    public Courier(String id, String name, Email email, PhoneNumber phoneNumber,
                   String passwordHash, Money salary, String vehicleType) {
        super(id, name, email, phoneNumber, passwordHash, salary);
        this.vehicleType = vehicleType;
        this.activeDeliveryIds = new ArrayList<>();
        this.maxActiveDeliveries = 2;
        this.completedDeliveriesCount = 0;
    }

    @Override
    public String getRole() {
        return "COURIER";
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void updateLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        this.currentLocation = location;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<String> getActiveDeliveryIds() {
        return Collections.unmodifiableList(activeDeliveryIds);
    }

    public int getActiveDeliveryCount() {
        return activeDeliveryIds.size();
    }

    public int getMaxActiveDeliveries() {
        return maxActiveDeliveries;
    }

    public void setMaxActiveDeliveries(int maxActiveDeliveries) {
        if (maxActiveDeliveries < 1) {
            throw new IllegalArgumentException("Max active deliveries must be at least 1");
        }
        this.maxActiveDeliveries = maxActiveDeliveries;
    }

    /**
     * Проверяет, может ли курьер принять еще одну доставку
     */
    @Override
    public boolean isAvailable() {
        return super.isAvailable() && activeDeliveryIds.size() < maxActiveDeliveries;
    }

    /**
     * Принять доставку
     */
    public void acceptDelivery(String deliveryId) {
        if (deliveryId == null || deliveryId.trim().isEmpty()) {
            throw new IllegalArgumentException("Delivery ID cannot be empty");
        }
        if (!isAvailable()) {
            throw new IllegalStateException("Courier is not available or at max capacity");
        }
        activeDeliveryIds.add(deliveryId);
    }

    /**
     * Завершить доставку
     */
    public void completeDelivery(String deliveryId) {
        if (!activeDeliveryIds.remove(deliveryId)) {
            throw new IllegalArgumentException("Delivery not found in active deliveries");
        }
        completedDeliveriesCount++;
    }

    public int getCompletedDeliveriesCount() {
        return completedDeliveriesCount;
    }
}