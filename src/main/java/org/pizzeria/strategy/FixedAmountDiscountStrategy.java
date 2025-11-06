package org.pizzeria.strategy;

import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.order.Order;

/**
 * Стратегия фиксированной скидки.
 * Применяет фиксированную сумму скидки.
 */
public class FixedAmountDiscountStrategy implements DiscountStrategy {
    private final Money discountAmount;
    private final Money minimumOrderAmount;

    public FixedAmountDiscountStrategy(Money discountAmount, Money minimumOrderAmount) {
        if (discountAmount == null) {
            throw new IllegalArgumentException("Discount amount cannot be null");
        }
        if (minimumOrderAmount == null) {
            throw new IllegalArgumentException("Minimum order amount cannot be null");
        }
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public FixedAmountDiscountStrategy(Money discountAmount) {
        this(discountAmount, Money.zero());
    }

    @Override
    public Money calculateDiscount(Order order) {
        if (!isApplicable(order)) {
            return Money.zero();
        }

        // Скидка не может быть больше суммы заказа
        if (order.getSubtotal().isLessThan(discountAmount)) {
            return order.getSubtotal();
        }

        return discountAmount;
    }

    @Override
    public String getDescription() {
        if (minimumOrderAmount.isZero()) {
            return String.format("%s discount", discountAmount);
        }
        return String.format("%s discount on orders over %s", discountAmount, minimumOrderAmount);
    }

    @Override
    public boolean isApplicable(Order order) {
        return order.getSubtotal().isGreaterThanOrEqual(minimumOrderAmount);
    }

    public Money getDiscountAmount() {
        return discountAmount;
    }

    public Money getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
