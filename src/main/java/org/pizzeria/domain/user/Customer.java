package com.ppois.pizzeria.domain.user;

import com.ppois.pizzeria.domain.common.Address;
import com.ppois.pizzeria.domain.common.Email;
import com.ppois.pizzeria.domain.common.PhoneNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Клиент пиццерии.
 * Может делать заказы, иметь несколько адресов доставки и бонусные баллы.
 */
public class Customer extends User {
    private final List<Address> deliveryAddresses;
    private Address defaultAddress;
    private int loyaltyPoints;
    private int totalOrders;

    public Customer(String name, Email email, PhoneNumber phoneNumber, String passwordHash) {
        super(name, email, phoneNumber, passwordHash);
        this.deliveryAddresses = new ArrayList<>();
        this.loyaltyPoints = 0;
        this.totalOrders = 0;
    }

    public Customer(String id, String name, Email email, PhoneNumber phoneNumber, String passwordHash) {
        super(id, name, email, phoneNumber, passwordHash);
        this.deliveryAddresses = new ArrayList<>();
        this.loyaltyPoints = 0;
        this.totalOrders = 0;
    }

    @Override
    public String getRole() {
        return "CUSTOMER";
    }

    // Управление адресами доставки
    public void addDeliveryAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        if (!deliveryAddresses.contains(address)) {
            deliveryAddresses.add(address);
        }
        // Если это первый адрес, делаем его по умолчанию
        if (deliveryAddresses.size() == 1) {
            defaultAddress = address;
        }
    }

    public void removeDeliveryAddress(Address address) {
        deliveryAddresses.remove(address);
        // Если удалили адрес по умолчанию, выбираем новый
        if (address.equals(defaultAddress) && !deliveryAddresses.isEmpty()) {
            defaultAddress = deliveryAddresses.get(0);
        } else if (deliveryAddresses.isEmpty()) {
            defaultAddress = null;
        }
    }

    public List<Address> getDeliveryAddresses() {
        return Collections.unmodifiableList(deliveryAddresses);
    }

    public Address getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Default address cannot be null");
        }
        if (!deliveryAddresses.contains(address)) {
            throw new IllegalArgumentException("Address must be added first");
        }
        this.defaultAddress = address;
    }

    // Система лояльности
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addLoyaltyPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        this.loyaltyPoints += points;
    }

    public void useLoyaltyPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        if (points > loyaltyPoints) {
            throw new IllegalArgumentException("Insufficient loyalty points");
        }
        this.loyaltyPoints -= points;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void incrementTotalOrders() {
        this.totalOrders++;
    }

    /**
     * Проверяет, является ли клиент VIP (более 10 заказов)
     */
    public boolean isVip() {
        return totalOrders >= 10;
    }
}