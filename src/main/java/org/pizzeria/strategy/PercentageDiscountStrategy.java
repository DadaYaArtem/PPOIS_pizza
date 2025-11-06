package org.pizzeria.strategy;

import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.order.Order;

/**
 * Стратегия процентной скидки.
 * Применяет фиксированный процент от суммы заказа.
 */
public class PercentageDiscountStrategy implements DiscountStrategy {
    private final int percentage;
    private final Money minimumOrderAmount;

    public PercentageDiscountStrategy(int percentage, Money minimumOrderAmount) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        if (minimumOrderAmount == null) {
            throw new IllegalArgumentException("Minimum order amount cannot be null");
        }
        this.percentage = percentage;
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public PercentageDiscountStrategy(int percentage) {
        this(percentage, Money.zero());
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
        if (minimumOrderAmount.isZero()) {
            return String.format("%d%% discount", percentage);
        }
        return String.format("%d%% discount on orders over %s", percentage, minimumOrderAmount);
    }

    @Override
    public boolean isApplicable(Order order) {
        return order.getSubtotal().isGreaterThanOrEqual(minimumOrderAmount);
    }

    public int getPercentage() {
        return percentage;
    }

    public Money getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
