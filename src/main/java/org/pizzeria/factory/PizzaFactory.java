package org.pizzeria.factory;

import org.pizzeria.builder.PizzaBuilder;
import org.pizzeria.domain.common.*;
import org.pizzeria.domain.menu.Ingredient;
import org.pizzeria.domain.menu.Pizza;
import org.pizzeria.domain.menu.Topping;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory для создания стандартных пицц.
 * Паттерн Factory упрощает создание объектов с предопределенными настройками.
 */
public class PizzaFactory {
    private static final Map<PizzaType, PizzaRecipe> recipes = new HashMap<>();

    static {
        // Инициализируем рецепты популярных пицц
        initializeRecipes();
    }

    /**
     * Создает пиццу по типу и размеру
     */
    public static Pizza createPizza(PizzaType type, PizzaSize size) {
        if (type == null) {
            throw new IllegalArgumentException("Pizza type cannot be null");
        }
        if (size == null) {
            throw new IllegalArgumentException("Pizza size cannot be null");
        }

        PizzaRecipe recipe = recipes.get(type);
        if (recipe == null) {
            throw new IllegalArgumentException("Unknown pizza type: " + type);
        }

        PizzaBuilder builder = new PizzaBuilder()
            .withName(recipe.name)
            .withDescription(recipe.description)
            .withSize(size)
            .withDoughType(recipe.doughType)
            .withBakeLevel(BakeLevel.NORMAL)
            .withBasePrice(recipe.basePrice);

        // Добавляем топпинги из рецепта
        for (Topping topping : recipe.toppings) {
            builder.addTopping(topping);
        }

        return builder.build();
    }

    /**
     * Создает пиццу по типу (размер Medium по умолчанию)
     */
    public static Pizza createPizza(PizzaType type) {
        return createPizza(type, PizzaSize.MEDIUM);
    }

    /**
     * Создает кастомную пиццу
     */
    public static Pizza createCustomPizza(String name, PizzaSize size, Money basePrice) {
        return new PizzaBuilder()
            .withName(name)
            .withDescription("Custom pizza")
            .withSize(size)
            .withDoughType(DoughType.TRADITIONAL)
            .withBakeLevel(BakeLevel.NORMAL)
            .withBasePrice(basePrice)
            .build();
    }

    /**
     * Инициализирует рецепты
     */
    private static void initializeRecipes() {
        // Маргарита
        recipes.put(PizzaType.MARGHERITA, new PizzaRecipe(
            "Margherita",
            "Classic pizza with tomato sauce, mozzarella, and basil",
            DoughType.TRADITIONAL,
            Money.of(8.99),
            new Topping(new Ingredient("Tomato Sauce", true), Money.of(0.50)),
            new Topping(new Ingredient("Mozzarella", true), Money.of(1.50)),
            new Topping(new Ingredient("Fresh Basil", true), Money.of(0.50))
        ));

        // Пепперони
        recipes.put(PizzaType.PEPPERONI, new PizzaRecipe(
            "Pepperoni",
            "Classic pepperoni pizza with tomato sauce and mozzarella",
            DoughType.TRADITIONAL,
            Money.of(10.99),
            new Topping(new Ingredient("Tomato Sauce", true), Money.of(0.50)),
            new Topping(new Ingredient("Mozzarella", true), Money.of(1.50)),
            new Topping(new Ingredient("Pepperoni", true), Money.of(2.00))
        ));

        // Гавайская
        recipes.put(PizzaType.HAWAIIAN, new PizzaRecipe(
            "Hawaiian",
            "Tropical pizza with ham and pineapple",
            DoughType.TRADITIONAL,
            Money.of(11.99),
            new Topping(new Ingredient("Tomato Sauce", true), Money.of(0.50)),
            new Topping(new Ingredient("Mozzarella", true), Money.of(1.50)),
            new Topping(new Ingredient("Ham", true), Money.of(2.00)),
            new Topping(new Ingredient("Pineapple", true), Money.of(1.00))
        ));

        // Четыре сыра
        recipes.put(PizzaType.FOUR_CHEESE, new PizzaRecipe(
            "Four Cheese",
            "Rich cheese pizza with four types of cheese",
            DoughType.TRADITIONAL,
            Money.of(12.99),
            new Topping(new Ingredient("Mozzarella", true), Money.of(1.50)),
            new Topping(new Ingredient("Parmesan", true), Money.of(1.50)),
            new Topping(new Ingredient("Gorgonzola", true), Money.of(1.50)),
            new Topping(new Ingredient("Goat Cheese", true), Money.of(1.50))
        ));

        // Вегетарианская
        recipes.put(PizzaType.VEGETARIAN, new PizzaRecipe(
            "Vegetarian",
            "Healthy pizza with fresh vegetables",
            DoughType.TRADITIONAL,
            Money.of(10.99),
            new Topping(new Ingredient("Tomato Sauce", true), Money.of(0.50)),
            new Topping(new Ingredient("Mozzarella", true), Money.of(1.50)),
            new Topping(new Ingredient("Bell Peppers", true), Money.of(1.00)),
            new Topping(new Ingredient("Mushrooms", true), Money.of(1.00)),
            new Topping(new Ingredient("Onions", true), Money.of(0.50)),
            new Topping(new Ingredient("Olives", true), Money.of(0.75))
        ));

        // Мясная
        recipes.put(PizzaType.MEAT_LOVERS, new PizzaRecipe(
            "Meat Lovers",
            "Ultimate meat pizza with multiple meat toppings",
            DoughType.TRADITIONAL,
            Money.of(13.99),
            new Topping(new Ingredient("Tomato Sauce", true), Money.of(0.50)),
            new Topping(new Ingredient("Mozzarella", true), Money.of(1.50)),
            new Topping(new Ingredient("Pepperoni", true), Money.of(2.00)),
            new Topping(new Ingredient("Sausage", true), Money.of(2.00)),
            new Topping(new Ingredient("Bacon", true), Money.of(2.00)),
            new Topping(new Ingredient("Ham", true), Money.of(2.00))
        ));
    }

    /**
     * Внутренний класс для хранения рецепта
     */
    private static class PizzaRecipe {
        final String name;
        final String description;
        final DoughType doughType;
        final Money basePrice;
        final Topping[] toppings;

        PizzaRecipe(String name, String description, DoughType doughType,
                   Money basePrice, Topping... toppings) {
            this.name = name;
            this.description = description;
            this.doughType = doughType;
            this.basePrice = basePrice;
            this.toppings = toppings;
        }
    }
}
