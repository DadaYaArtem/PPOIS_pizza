package com.ppois.pizzeria.domain.common;

/**
 * Типы готовых пицц (используется в фабрике)
 */
public enum PizzaType {
    MARGHERITA("Margherita", "Classic tomato sauce, mozzarella, basil", 8.99),
    PEPPERONI("Pepperoni", "Tomato sauce, mozzarella, pepperoni", 10.99),
    HAWAIIAN("Hawaiian", "Tomato sauce, mozzarella, ham, pineapple", 11.99),
    FOUR_CHEESE("Four Cheese", "Mozzarella, parmesan, gorgonzola, ricotta", 12.99),
    VEGETARIAN("Vegetarian", "Tomato sauce, mozzarella, peppers, mushrooms, onions", 10.99),
    MEAT_LOVERS("Meat Lovers", "Tomato sauce, mozzarella, pepperoni, sausage, bacon, ham", 14.99),
    BBQ_CHICKEN("BBQ Chicken", "BBQ sauce, mozzarella, chicken, red onion", 13.99),
    SEAFOOD("Seafood", "Tomato sauce, mozzarella, shrimp, mussels, calamari", 15.99);

    private final String displayName;
    private final String description;
    private final double basePrice;

    PizzaType(String displayName, String description, double basePrice) {
        this.displayName = displayName;
        this.description = description;
        this.basePrice = basePrice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        return displayName;
    }
}