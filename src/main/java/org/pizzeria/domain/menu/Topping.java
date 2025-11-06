package org.pizzeria.domain.menu;

import org.pizzeria.domain.common.Money;

/**
 * Топпинг (добавка) для пиццы.
 * Используется в Builder Pattern.
 */
public class Topping {
    private final Ingredient ingredient;
    private final Money price;

    public Topping(Ingredient ingredient, Money price) {
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient cannot be null");
        }
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        this.ingredient = ingredient;
        this.price = price;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Money getPrice() {
        return price;
    }

    public String getName() {
        return ingredient.getName();
    }

    public boolean isAvailable() {
        return ingredient.isInStock();
    }

    @Override
    public String toString() {
        return ingredient.getName();
    }
}