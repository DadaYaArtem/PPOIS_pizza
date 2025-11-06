package org.pizzeria.domain.payment;

import org.pizzeria.domain.common.Money;

/**
 * Наличный расчет как способ оплаты.
 * Простой класс с минимальной логикой.
 */
public class Cash implements PaymentMethod {
    private final Money amountProvided; // сумма, которую дал клиент
    private final boolean needsChange;

    public Cash() {
        this.amountProvided = Money.zero();
        this.needsChange = false;
    }

    public Cash(Money amountProvided) {
        if (amountProvided == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        this.amountProvided = amountProvided;
        this.needsChange = false;
    }

    public Cash(Money amountProvided, boolean needsChange) {
        if (amountProvided == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        this.amountProvided = amountProvided;
        this.needsChange = needsChange;
    }

    public Money getAmountProvided() {
        return amountProvided;
    }

    public boolean needsChange() {
        return needsChange;
    }

    /**
     * Вычисляет сдачу
     */
    public Money calculateChange(Money orderTotal) {
        if (orderTotal == null) {
            throw new IllegalArgumentException("Order total cannot be null");
        }
        if (amountProvided.isGreaterThan(orderTotal)) {
            return amountProvided.subtract(orderTotal);
        }
        return Money.zero();
    }

    @Override
    public String getType() {
        return "CASH";
    }

    @Override
    public String getDescription() {
        return "Cash payment";
    }

    @Override
    public boolean isValid() {
        return true; // наличные всегда валидны
    }

    @Override
    public String getMaskedInfo() {
        if (amountProvided.isZero()) {
            return "Cash";
        }
        return String.format("Cash (%s provided)", amountProvided);
    }

    @Override
    public String toString() {
        return getMaskedInfo();
    }
}
