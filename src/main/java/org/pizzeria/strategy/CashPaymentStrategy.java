package org.pizzeria.strategy;

import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.payment.Cash;
import org.pizzeria.domain.payment.Payment;
import org.pizzeria.domain.payment.PaymentMethod;

import java.util.UUID;

/**
 * Стратегия обработки наличных платежей.
 * Наличные платежи не требуют онлайн обработки.
 */
public class CashPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(Payment payment) {
        PaymentMethod method = payment.getPaymentMethod();

        if (!canProcess(method)) {
            payment.fail("Invalid payment method");
            return false;
        }

        Cash cash = (Cash) method;

        try {
            // Начинаем обработку
            payment.startProcessing();

            // Проверяем, достаточно ли наличных
            Money amountProvided = cash.getAmountProvided();
            Money orderTotal = payment.getAmount();

            if (!amountProvided.isZero() && amountProvided.isLessThan(orderTotal)) {
                payment.fail("Insufficient cash provided");
                return false;
            }

            // Вычисляем сдачу
            if (!amountProvided.isZero()) {
                Money change = cash.calculateChange(orderTotal);
                if (!change.isZero()) {
                    payment.setNotes(String.format("Change: %s", change));
                }
            }

            // Наличные платежи обрабатываются сразу
            String transactionId = generateReceiptNumber();
            payment.complete(transactionId);

            System.out.printf("Cash payment received: %s (change: %s)%n",
                orderTotal, cash.calculateChange(orderTotal));

            return true;
        } catch (Exception e) {
            payment.fail("Error processing cash payment: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean canProcess(PaymentMethod paymentMethod) {
        return paymentMethod instanceof Cash;
    }

    @Override
    public String getDescription() {
        return "Cash Payment (no fees)";
    }

    @Override
    public Money calculateFee(Money amount) {
        // Наличные платежи без комиссии
        return Money.zero();
    }

    /**
     * Генерирует номер чека
     */
    private String generateReceiptNumber() {
        return "RCPT-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
