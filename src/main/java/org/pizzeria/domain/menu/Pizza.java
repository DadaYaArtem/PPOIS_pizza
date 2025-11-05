package com.ppois.pizzeria.domain.menu;

import com.ppois.pizzeria.domain.common.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Пицца - главный продукт пиццерии.
 * Может быть создана через Builder или Factory.
 */
public class Pizza implements MenuItem {
    private final String id;
    private final String name;
    private String description;
    private PizzaSize size;
    private DoughType doughType;
    private BakeLevel bakeLevel;
    private final List<Topping> toppings;
    private Money basePrice;
    private boolean available;

    // Конструктор для Builder
    public Pizza(String name, String description, PizzaSize size, 
                 DoughType doughType, BakeLevel bakeLevel, 
                 List<Topping> toppings, Money basePrice) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.size = size;
        this.doughType = doughType;
        this.bakeLevel = bakeLevel;
        this.toppings = new ArrayList<>(toppings);
        this.basePrice = basePrice;
        this.available = true;
    }

    // Конструктор с ID (для тестов)
    public Pizza(String id, String name, PizzaSize size, DoughType doughType, Money basePrice) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.size = size;
        this.doughType = doughType;
        this.bakeLevel = BakeLevel.NORMAL;
        this.toppings = new ArrayList<>();
        this.basePrice = basePrice;
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

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public MenuCategory getCategory() {
        return MenuCategory.PIZZA;
    }

    public PizzaSize getSize() {
        return size;
    }

    public DoughType getDoughType() {
        return doughType;
    }

    public BakeLevel getBakeLevel() {
        return bakeLevel;
    }

    public List<Topping> getToppings() {
        return Collections.unmodifiableList(toppings);
    }

    public Money getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Money basePrice) {
        if (basePrice == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        this.basePrice = basePrice;
    }

    /**
     * Рассчитывает полную стоимость пиццы с учетом размера, теста и топпингов
     */
    @Override
    public Money getPrice() {
        Money price = basePrice;
        
        // Умножаем на множитель размера
        price = price.multiply(size.getPriceMultiplier());
        
        // Добавляем стоимость теста
        price = price.add(Money.of(doughType.getAdditionalPrice()));
        
        // Добавляем стоимость топпингов
        for (Topping topping : toppings) {
            price = price.add(topping.getPrice());
        }
        
        return price;
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
        return String.format("%s (%s, %s) - %s", 
            name, size.getDisplayName(), doughType.getDisplayName(), getPrice());
    }
}