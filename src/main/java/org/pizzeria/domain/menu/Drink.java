package com.ppois.pizzeria.domain.menu;

import com.ppois.pizzeria.domain.common.MenuCategory;
import com.ppois.pizzeria.domain.common.Money;

import java.util.UUID;

/**
 * Напиток.
 */
public class Drink implements MenuItem {
    private final String id;
    private String name;
    private String description;
    private int volumeMl;
    private Money price;
    private boolean available;
    private boolean isCarbonated;

    public Drink(String name, String description, int volumeMl, Money price, boolean isCarbonated) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        this.id = UUID.randomUUID().toString();
        this.name = name.trim();
        this.description = description;
        this.volumeMl = volumeMl;
        this.price = price;
        this.isCarbonated = isCarbonated;
        this.available = true;
    }

    public Drink(String id, String name, int volumeMl, Money price) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.volumeMl = volumeMl;
        this.price = price;
        this.isCarbonated = false;
        this.available = true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name.trim();
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public MenuCategory getCategory() {
        return MenuCategory.DRINK;
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(int volumeMl) {
        if (volumeMl <= 0) {
            throw new IllegalArgumentException("Volume must be positive");
        }
        this.volumeMl = volumeMl;
    }

    @Override
    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        this.price = price;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isCarbonated() {
        return isCarbonated;
    }

    public void setCarbonated(boolean carbonated) {
        isCarbonated = carbonated;
    }

    @Override
    public String toString() {
        return String.format("%s (%dml) - %s", name, volumeMl, price);
    }
}