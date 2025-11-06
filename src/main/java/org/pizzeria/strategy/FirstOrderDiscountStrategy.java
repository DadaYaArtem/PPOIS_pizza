package org.pizzeria.strategy;

import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.order.Order;

/**
 * Стратегия скидки на первый заказ.
 * Дает скидку для новых клиентов.
 */
public class FirstOrderDiscountStrategy implements DiscountStrategy {
    private final int percentage;

    public FirstOrderDiscountStrategy(int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        this.percentage = percentage;
    }

    public FirstOrderDiscountStrategy() {
        this(15); // 15% по умолчанию
    }

    @Override
    public Money calculateDiscount(Order order) {
        if (!isApplicable(order)) {
            return Money.zero();
        }
        return order.getSubtotal().percentage(percentage);
    }

    @Override
    public String getDescription() {
        return String.format("%d%% discount for first order", percentage);
    }

    @Override
    public boolean isApplicable(Order order) {
        // В реальном приложении здесь была бы проверка истории заказов клиента
        // Для простоты всегда возвращаем true
        return true;
    }

    public int getPercentage() {
        return percentage;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
