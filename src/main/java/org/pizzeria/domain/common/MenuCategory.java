package org.pizzeria.domain.common;

/**
 * Категории меню
 */
public enum MenuCategory {
    PIZZA("Pizza", "All types of pizza"),
    DRINK("Drinks", "Beverages"),
    DESSERT("Desserts", "Sweet treats"),
    APPETIZER("Appetizers", "Starters and sides"),
    SAUCE("Sauces", "Extra sauces"),
    COMBO("Combo Meals", "Special combo offers");

    private final String displayName;
    private final String description;

    MenuCategory(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return displayName;
    }
}