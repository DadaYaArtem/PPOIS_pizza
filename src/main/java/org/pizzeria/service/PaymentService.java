package org.pizzeria.service;

import org.pizzeria.domain.order.Order;
import org.pizzeria.domain.payment.Payment;
import org.pizzeria.domain.payment.PaymentMethod;
import org.pizzeria.strategy.PaymentStrategy;
import org.pizzeria.validator.PaymentValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис для управления платежами.
 * Координирует обработку платежей с использованием различных стратегий.
 */
public class PaymentService {
    private final List<Payment> payments;
    private final Map<String, PaymentStrategy> strategies;

    public PaymentService() {
        this.payments = new ArrayList<>();
        this.strategies = new HashMap<>();
    }

    /**
     * Регистрирует стратегию обработки платежей
     */
    public void registerStrategy(String name, PaymentStrategy strategy) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Strategy name cannot be empty");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        strategies.put(name, strategy);
    }

    /**
     * Создает платеж для заказа
     */
    public Payment createPayment(Order order, PaymentMethod paymentMethod) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }

        Payment payment = new Payment(order, paymentMethod, order.getTotal());
        payments.add(payment);

        return payment;
    }

    /**
     * Обрабатывает платеж с использованием соответствующей стратегии
     */
    public boolean processPayment(Payment payment) {
        if (!payments.contains(payment)) {
            throw new IllegalArgumentException("Payment not found");
        }

        // Валидируем платеж
        PaymentValidator.validateAndThrow(payment);

        // Находим подходящую стратегию
        PaymentStrategy strategy = findStrategyForPayment(payment);
        if (strategy == null) {
            payment.fail("No suitable payment strategy found");
            return false;
        }

        // Обрабатываем платеж
        return strategy.processPayment(payment);
    }

    /**
     * Возвращает платеж
     */
    public void refundPayment(Payment payment) {
        if (!payments.contains(payment)) {
            throw new IllegalArgumentException("Payment not found");
        }

        if (!payment.isRefundable()) {
            throw new IllegalStateException("Payment cannot be refunded");
        }

        payment.refund();
    }

    /**
     * Находит подходящую стратегию для платежа
     */
    private PaymentStrategy findStrategyForPayment(Payment payment) {
        PaymentMethod method = payment.getPaymentMethod();

        for (PaymentStrategy strategy : strategies.values()) {
            if (strategy.canProcess(method)) {
                return strategy;
            }
        }

        return null;
    }

    /**
     * Находит платеж по ID
     */
    public Optional<Payment> findPaymentById(String paymentId) {
        return payments.stream()
            .filter(p -> p.getId().equals(paymentId))
            .findFirst();
    }

    /**
     * Находит платеж для заказа
     */
    public Optional<Payment> findPaymentByOrder(Order order) {
        return payments.stream()
            .filter(p -> p.getOrder().equals(order))
            .findFirst();
    }

    /**
     * Находит все успешные платежи
     */
    public List<Payment> findSuccessfulPayments() {
        return payments.stream()
            .filter(Payment::isSuccessful)
            .toList();
    }

    /**
     * Возвращает все платежи
     */
    public List<Payment> getAllPayments() {
        return new ArrayList<>(payments);
    }

    /**
     * Удаляет платеж (только для тестов)
     */
    void removePayment(Payment payment) {
        payments.remove(payment);
    }
}
