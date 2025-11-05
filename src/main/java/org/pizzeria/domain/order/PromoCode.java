package com.ppois.pizzeria.domain.order;

import com.ppois.pizzeria.domain.common.Money;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Промокод для скидок.
 */
public class PromoCode {
    private final String id;
    private final String code;
    private final String description;
    private final Money discountAmount;
    private final int discountPercentage; // если 0, используется discountAmount
    private final LocalDate validFrom;
    private final LocalDate validUntil;
    private int maxUsages;
    private int currentUsages;
    private boolean isActive;

    public PromoCode(String code, String description, Money discountAmount, 
                     LocalDate validFrom, LocalDate validUntil, int maxUsages) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Promo code cannot be empty");
        }
        this.id = UUID.randomUUID().toString();
        this.code = code.trim().toUpperCase();
        this.description = description;
        this.discountAmount = discountAmount != null ? discountAmount : Money.zero();
        this.discountPercentage = 0;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.maxUsages = maxUsages;
        this.currentUsages = 0;
        this.isActive = true;
    }

    public PromoCode(String code, String description, int discountPercentage,
                     LocalDate validFrom, LocalDate validUntil, int maxUsages) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Promo code cannot be empty");
        }
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
        this.id = UUID.randomUUID().toString();
        this.code = code.trim().toUpperCase();
        this.description = description;
        this.discountAmount = Money.zero();
        this.discountPercentage = discountPercentage;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.maxUsages = maxUsages;
        this.currentUsages = 0;
        this.isActive = true;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Money getDiscountAmount() {
        return discountAmount;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public boolean isPercentageDiscount() {
        return discountPercentage > 0;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public int getMaxUsages() {
        return maxUsages;
    }

    public int getCurrentUsages() {
        return currentUsages;
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    /**
     * Проверяет, валиден ли промокод на текущую дату
     */
    public boolean isValid() {
        if (!isActive) {
            return false;
        }
        
        LocalDate now = LocalDate.now();
        
        if (validFrom != null && now.isBefore(validFrom)) {
            return false;
        }
        
        if (validUntil != null && now.isAfter(validUntil)) {
            return false;
        }
        
        if (maxUsages > 0 && currentUsages >= maxUsages) {
            return false;
        }
        
        return true;
    }

    /**
     * Использовать промокод
     */
    public void use() {
        if (!isValid()) {
            throw new IllegalStateException("Promo code is not valid");
        }
        currentUsages++;
    }

    /**
     * Рассчитать скидку для заказа
     */
    public Money calculateDiscount(Money orderSubtotal) {
        if (!isValid()) {
            return Money.zero();
        }
        
        if (isPercentageDiscount()) {
            return orderSubtotal.percentage(discountPercentage);
        } else {
            // Фиксированная скидка, но не больше суммы заказа
            if (discountAmount.isGreaterThan(orderSubtotal)) {
                return orderSubtotal;
            }
            return discountAmount;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoCode promoCode = (PromoCode) o;
        return Objects.equals(id, promoCode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("PromoCode{code='%s', discount=%s%s}", 
            code, 
            isPercentageDiscount() ? discountPercentage + "%" : discountAmount.toString(),
            isValid() ? "" : " (expired)");
    }
}