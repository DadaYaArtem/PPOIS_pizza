package org.pizzeria.domain.common;

/**
 * Размеры пиццы
 */
public enum PizzaSize {
    SMALL("Small", 25, 1.0),
    MEDIUM("Medium", 30, 1.5),
    LARGE("Large", 35, 2.0),
    EXTRA_LARGE("Extra Large", 40, 2.5);

    private final String displayName;
    private final int diameterCm;
    private final double priceMultiplier;

    PizzaSize(String displayName, int diameterCm, double priceMultiplier) {
        this.displayName = displayName;
        this.diameterCm = diameterCm;
        this.priceMultiplier = priceMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDiameterCm() {
        return diameterCm;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    @Override
    public String toString() {
        return displayName;
    }
}