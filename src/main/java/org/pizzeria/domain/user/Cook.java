package org.pizzeria.domain.user;

import org.pizzeria.domain.common.Email;
import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.common.PhoneNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Повар пиццерии.
 * Принимает заказы в работу и готовит их.
 */
public class Cook extends Employee {
    private String specialization; // например: "Pizza Master", "Desserts", "All-rounder"
    private final List<String> currentOrderIds; // ID заказов в работе
    private int maxConcurrentOrders; // максимальное количество одновременных заказов
    private int completedOrdersCount;

    public Cook(String name, Email email, PhoneNumber phoneNumber, 
                String passwordHash, Money salary, String specialization) {
        super(name, email, phoneNumber, passwordHash, salary);
        this.specialization = specialization != null ? specialization : "All-rounder";
        this.currentOrderIds = new ArrayList<>();
        this.maxConcurrentOrders = 3;
        this.completedOrdersCount = 0;
    }

    public Cook(String id, String name, Email email, PhoneNumber phoneNumber,
                String passwordHash, Money salary, String specialization) {
        super(id, name, email, phoneNumber, passwordHash, salary);
        this.specialization = specialization;
        this.currentOrderIds = new ArrayList<>();
        this.maxConcurrentOrders = 3;
        this.completedOrdersCount = 0;
    }

    @Override
    public String getRole() {
        return "COOK";
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<String> getCurrentOrderIds() {
        return Collections.unmodifiableList(currentOrderIds);
    }

    public int getCurrentOrderCount() {
        return currentOrderIds.size();
    }

    public int getMaxConcurrentOrders() {
        return maxConcurrentOrders;
    }

    public void setMaxConcurrentOrders(int maxConcurrentOrders) {
        if (maxConcurrentOrders < 1) {
            throw new IllegalArgumentException("Max concurrent orders must be at least 1");
        }
        this.maxConcurrentOrders = maxConcurrentOrders;
    }

    /**
     * Проверяет, может ли повар принять еще один заказ
     */
    @Override
    public boolean isAvailable() {
        return super.isAvailable() && currentOrderIds.size() < maxConcurrentOrders;
    }

    /**
     * Принять заказ в работу
     */
    public void acceptOrder(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        if (!isAvailable()) {
            throw new IllegalStateException("Cook is not available or at max capacity");
        }
        currentOrderIds.add(orderId);
    }

    /**
     * Завершить работу над заказом
     */
    public void completeOrder(String orderId) {
        if (!currentOrderIds.remove(orderId)) {
            throw new IllegalArgumentException("Order not found in current orders");
        }
        completedOrdersCount++;
    }

    public int getCompletedOrdersCount() {
        return completedOrdersCount;
    }
}