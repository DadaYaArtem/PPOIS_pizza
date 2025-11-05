package com.ppois.pizzeria.domain.menu;

import java.util.Objects;
import java.util.UUID;

/**
 * Ингредиент для пиццы.
 */
public class Ingredient {
    private final String id;
    private final String name;
    private double pricePerUnit;
    private int stockQuantity;

    public Ingredient(String name, double pricePerUnit) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient name cannot be empty");
        }
        this.id = UUID.randomUUID().toString();
        this.name = name.trim();
        this.pricePerUnit = pricePerUnit;
        this.stockQuantity = 100; // начальный запас
    }

    public Ingredient(String id, String name, double pricePerUnit, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.stockQuantity = stockQuantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        if (pricePerUnit < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.pricePerUnit = pricePerUnit;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void addStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.stockQuantity += quantity;
    }

    public void useStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (quantity > stockQuantity) {
            throw new IllegalArgumentException("Not enough stock");
        }
        this.stockQuantity -= quantity;
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }
}