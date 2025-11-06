package org.pizzeria.builder;

import org.pizzeria.domain.common.BakeLevel;
import org.pizzeria.domain.common.DoughType;
import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.common.PizzaSize;
import org.pizzeria.domain.menu.Pizza;
import org.pizzeria.domain.menu.Topping;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder для создания пиццы.
 * Паттерн Builder позволяет пошагово конструировать сложный объект.
 */
public class PizzaBuilder {
    private String name;
    private String description;
    private PizzaSize size;
    private DoughType doughType;
    private BakeLevel bakeLevel;
    private List<Topping> toppings;
    private Money basePrice;

    public PizzaBuilder() {
        this.toppings = new ArrayList<>();
        // Значения по умолчанию
        this.size = PizzaSize.MEDIUM;
        this.doughType = DoughType.TRADITIONAL;
        this.bakeLevel = BakeLevel.NORMAL;
        this.description = "";
    }

    /**
     * Устанавливает название пиццы
     */
    public PizzaBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Устанавливает описание пиццы
     */
    public PizzaBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Устанавливает размер пиццы
     */
    public PizzaBuilder withSize(PizzaSize size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }
        this.size = size;
        return this;
    }

    /**
     * Устанавливает тип теста
     */
    public PizzaBuilder withDoughType(DoughType doughType) {
        if (doughType == null) {
            throw new IllegalArgumentException("Dough type cannot be null");
        }
        this.doughType = doughType;
        return this;
    }

    /**
     * Устанавливает уровень прожарки
     */
    public PizzaBuilder withBakeLevel(BakeLevel bakeLevel) {
        if (bakeLevel == null) {
            throw new IllegalArgumentException("Bake level cannot be null");
        }
        this.bakeLevel = bakeLevel;
        return this;
    }

    /**
     * Добавляет топпинг
     */
    public PizzaBuilder addTopping(Topping topping) {
        if (topping == null) {
            throw new IllegalArgumentException("Topping cannot be null");
        }
        if (!topping.isAvailable()) {
            throw new IllegalArgumentException("Topping is not available: " + topping.getName());
        }
        this.toppings.add(topping);
        return this;
    }

    /**
     * Удаляет топпинг
     */
    public PizzaBuilder removeTopping(Topping topping) {
        this.toppings.remove(topping);
        return this;
    }

    /**
     * Очищает все топпинги
     */
    public PizzaBuilder clearToppings() {
        this.toppings.clear();
        return this;
    }

    /**
     * Устанавливает базовую цену
     */
    public PizzaBuilder withBasePrice(Money basePrice) {
        if (basePrice == null) {
            throw new IllegalArgumentException("Base price cannot be null");
        }
        this.basePrice = basePrice;
        return this;
    }

    /**
     * Устанавливает базовую цену (double)
     */
    public PizzaBuilder withBasePrice(double basePrice) {
        return withBasePrice(Money.of(basePrice));
    }

    /**
     * Создает объект пиццы
     */
    public Pizza build() {
        // Валидация обязательных полей
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Pizza name is required");
        }
        if (basePrice == null) {
            throw new IllegalStateException("Base price is required");
        }

        return new Pizza(
            name.trim(),
            description,
            size,
            doughType,
            bakeLevel,
            new ArrayList<>(toppings), // копируем список
            basePrice
        );
    }

    /**
     * Сбрасывает builder к начальному состоянию
     */
    public PizzaBuilder reset() {
        this.name = null;
        this.description = "";
        this.size = PizzaSize.MEDIUM;
        this.doughType = DoughType.TRADITIONAL;
        this.bakeLevel = BakeLevel.NORMAL;
        this.toppings.clear();
        this.basePrice = null;
        return this;
    }

    /**
     * Создает копию существующей пиццы для модификации
     */
    public static PizzaBuilder from(Pizza pizza) {
        PizzaBuilder builder = new PizzaBuilder();
        builder.name = pizza.getName();
        builder.description = pizza.getDescription();
        builder.size = pizza.getSize();
        builder.doughType = pizza.getDoughType();
        builder.bakeLevel = pizza.getBakeLevel();
        builder.toppings = new ArrayList<>(pizza.getToppings());
        builder.basePrice = pizza.getBasePrice();
        return builder;
    }
}
