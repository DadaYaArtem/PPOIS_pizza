package com.ppois.pizzeria.domain.menu;

import com.ppois.pizzeria.domain.common.MenuCategory;
import com.ppois.pizzeria.domain.common.Money;

/**
 * Базовый интерфейс для всех позиций меню.
 * Полиморфизм - все позиции меню взаимозаменяемы.
 */
public interface MenuItem {
    String getId();
    String getName();
    String getDescription();
    Money getPrice();
    MenuCategory getCategory();
    boolean isAvailable();
    void setAvailable(boolean available);
}