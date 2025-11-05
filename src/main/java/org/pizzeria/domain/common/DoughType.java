package com.ppois.pizzeria.domain.common;

/**
 * Типы теста для пиццы
 */
public enum DoughType {
    THIN("Thin Crust", 0.0),
    TRADITIONAL("Traditional", 1.0),
    THICK("Thick Crust", 2.0),
    CHEESE_STUFFED("Cheese Stuffed", 3.5),
    GLUTEN_FREE("Gluten Free", 4.0);

    private final String displayName;
    private final double additionalPrice;

    DoughType(String displayName, double additionalPrice) {
        this.displayName = displayName;
        this.additionalPrice = additionalPrice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getAdditionalPrice() {
        return additionalPrice;
    }

    @Override
    public String toString() {
        return displayName;
    }
}