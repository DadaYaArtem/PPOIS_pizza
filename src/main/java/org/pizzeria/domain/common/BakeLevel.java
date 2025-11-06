package org.pizzeria.domain.common;

/**
 * Степень прожарки пиццы
 */
public enum BakeLevel {
    LIGHT("Light Bake", "Slightly undercooked, softer"),
    NORMAL("Normal Bake", "Standard baking"),
    WELL_DONE("Well Done", "Extra crispy, darker crust");

    private final String displayName;
    private final String description;

    BakeLevel(String displayName, String description) {
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