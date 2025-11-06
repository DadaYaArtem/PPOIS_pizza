package org.pizzeria.factory;

import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.menu.Dessert;
import org.pizzeria.domain.menu.Drink;
import org.pizzeria.domain.menu.MenuItem;

import java.util.UUID;

/**
 * Factory для создания элементов меню (напитки, десерты).
 * Упрощает создание стандартных позиций меню.
 */
public class MenuItemFactory {

    /**
     * Создает напиток
     */
    public static Drink createDrink(String name, String description, int volumeMl,
                                   double price, boolean isCarbonated) {
        return new Drink(name, description, volumeMl, Money.of(price), isCarbonated);
    }

    /**
     * Создает десерт
     */
    public static Dessert createDessert(String name, String description,
                                       int weightGrams, double price) {
        return new Dessert(
            UUID.randomUUID().toString(),
            name,
            weightGrams,
            Money.of(price)
        );
    }

    /**
     * Создает популярные напитки
     */
    public static class Drinks {
        public static Drink createCocaCola() {
            return createDrink("Coca-Cola", "Classic Coca-Cola", 330, 2.50, true);
        }

        public static Drink createSprite() {
            return createDrink("Sprite", "Lemon-lime soda", 330, 2.50, true);
        }

        public static Drink createFanta() {
            return createDrink("Fanta", "Orange flavored soda", 330, 2.50, true);
        }

        public static Drink createWater() {
            return createDrink("Water", "Still mineral water", 500, 1.50, false);
        }

        public static Drink createSparklingWater() {
            return createDrink("Sparkling Water", "Carbonated mineral water", 500, 1.75, true);
        }

        public static Drink createOrangeJuice() {
            return createDrink("Orange Juice", "Fresh orange juice", 250, 3.00, false);
        }

        public static Drink createAppleJuice() {
            return createDrink("Apple Juice", "Fresh apple juice", 250, 3.00, false);
        }

        public static Drink createIcedTea() {
            return createDrink("Iced Tea", "Lemon flavored iced tea", 330, 2.75, false);
        }
    }

    /**
     * Создает популярные десерты
     */
    public static class Desserts {
        public static Dessert createTiramisu() {
            return createDessert("Tiramisu", "Classic Italian dessert with coffee and mascarpone", 150, 5.99);
        }

        public static Dessert createCheesecake() {
            return createDessert("Cheesecake", "New York style cheesecake", 120, 5.50);
        }

        public static Dessert createChocolateCake() {
            return createDessert("Chocolate Cake", "Rich chocolate cake", 130, 5.75);
        }

        public static Dessert createIceCream() {
            return createDessert("Ice Cream", "Vanilla ice cream", 100, 3.50);
        }

        public static Dessert createBrownie() {
            return createDessert("Brownie", "Chocolate brownie", 80, 3.99);
        }

        public static Dessert createPannaCotta() {
            return createDessert("Panna Cotta", "Italian cream dessert with berry sauce", 120, 4.99);
        }

        public static Dessert createCannoli() {
            return createDessert("Cannoli", "Sicilian pastry with sweet ricotta filling", 90, 4.50);
        }
    }

    /**
     * Создает комбо-набор (может быть расширен в будущем)
     */
    public static MenuItem[] createComboSet() {
        return new MenuItem[] {
            Drinks.createCocaCola(),
            Desserts.createIceCream()
        };
    }
}
