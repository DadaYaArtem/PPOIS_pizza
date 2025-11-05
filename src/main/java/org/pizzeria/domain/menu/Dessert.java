package com.ppois.pizzeria.domain.menu;

import com.ppois.pizzeria.domain.common.MenuCategory;
import com.ppois.pizzeria.domain.common.Money;

/**
 * Десерт.
 */
public class Dessert implements MenuItem {
    private final String id;
    private String name;
    private String description;
    private int weightGrams;
    private Money price;
    private boolean available;

    public Dessert(String id, String name, int weightGrams, Money price) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.weightGrams = weightGrams;
        this.price = price;
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
        return MenuCategory.DESSERT;
    }

    public int getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(int weightGrams) {
        if (weightGrams <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.weightGrams = weightGrams;
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

    @Override
    public String toString() {
        return String.format("%s (%dg) - %s", name, weightGrams, price);
    }
}