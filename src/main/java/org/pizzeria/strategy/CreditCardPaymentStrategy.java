package org.pizzeria.strategy;

import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.payment.CreditCard;
import org.pizzeria.domain.payment.Payment;
import org.pizzeria.domain.payment.PaymentMethod;

import java.util.UUID;

/**
 * Стратегия обработки платежей по кредитной карте.
 */
public class CreditCardPaymentStrategy implements PaymentStrategy {
    private final double feePercentage;

    public CreditCardPaymentStrategy(double feePercentage) {
        if (feePercentage < 0) {
            throw new IllegalArgumentException("Fee percentage cannot be negative");
        }
        this.feePercentage = feePercentage;
    }

    public CreditCardPaymentStrategy() {
        this(2.5); // 2.5% комиссия по умолчанию
    }

    @Override
    public boolean processPayment(Payment payment) {
        PaymentMethod method = payment.getPaymentMethod();

        if (!canProcess(method)) {
            payment.fail("Invalid payment method");
            return false;
        }

        CreditCard card = (CreditCard) method;

        // Проверяем валидность карты
        if (!card.isValid()) {
            payment.fail("Invalid or expired card");
            return false;
        }

        try {
            // Начинаем обработку
            payment.startProcessing();

            // Имитация обработки платежа через платежный шлюз
            boolean success = processWithPaymentGateway(card, payment.getAmount());

            if (success) {
                String transactionId = generateTransactionId();
                payment.complete(transactionId);
                return true;
            } else {
                payment.fail("Payment declined by bank");
                return false;
            }
        } catch (Exception e) {
            payment.fail("Error processing payment: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean canProcess(PaymentMethod paymentMethod) {
        return paymentMethod instanceof CreditCard;
    }

    @Override
    public String getDescription() {
        return String.format("Credit Card Payment (%.1f%% fee)", feePercentage);
    }

    @Override
    public Money calculateFee(Money amount) {
        return amount.multiply(feePercentage / 100.0);
    }

    /**
     * Имитация обработки через платежный шлюз
     */
    private boolean processWithPaymentGateway(CreditCard card, Money amount) {
        // В реальном приложении здесь была бы интеграция с Stripe, PayPal и т.д.
        System.out.printf("Processing card payment: %s for amount %s%n",
            card.getMaskedInfo(), amount);

        // Имитируем успешную обработку
        return true;
    }

    /**
     * Генерирует ID транзакции
     */
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }

    public double getFeePercentage() {
        return feePercentage;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
