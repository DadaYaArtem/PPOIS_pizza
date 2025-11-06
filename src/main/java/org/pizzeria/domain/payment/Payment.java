package org.pizzeria.domain.payment;

import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.common.PaymentStatus;
import org.pizzeria.domain.order.Order;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Платеж за заказ.
 * Связывает Order с PaymentMethod и отслеживает статус оплаты.
 */
public class Payment {
    private final String id;
    private final Order order;
    private final PaymentMethod paymentMethod;
    private final Money amount;
    private PaymentStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private LocalDateTime completedAt;
    private String transactionId; // ID транзакции от платежной системы
    private String errorMessage;
    private String notes;

    public Payment(Order order, PaymentMethod paymentMethod, Money amount) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }
        if (amount == null || amount.isZero()) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (!paymentMethod.isValid()) {
            throw new IllegalArgumentException("Payment method is not valid");
        }

        this.id = UUID.randomUUID().toString();
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.notes = "";
    }

    // Конструктор для тестов
    public Payment(String id, Order order, PaymentMethod paymentMethod) {
        this.id = id;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.amount = order.getTotal();
        this.status = PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.notes = "";
    }

    public String getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Money getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;

        // Автоматически проставляем временные метки
        if (status == PaymentStatus.PROCESSING && processedAt == null) {
            processedAt = LocalDateTime.now();
        }
        if (status.isFinal() && completedAt == null) {
            completedAt = LocalDateTime.now();
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes != null ? notes : "";
    }

    /**
     * Начинает обработку платежа
     */
    public void startProcessing() {
        if (status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Cannot process payment in status: " + status);
        }
        setStatus(PaymentStatus.PROCESSING);
    }

    /**
     * Отмечает платеж как успешный
     */
    public void complete(String transactionId) {
        if (status != PaymentStatus.PROCESSING) {
            throw new IllegalStateException("Cannot complete payment in status: " + status);
        }
        this.transactionId = transactionId;
        setStatus(PaymentStatus.COMPLETED);
    }

    /**
     * Отмечает платеж как неудачный
     */
    public void fail(String errorMessage) {
        if (status.isFinal()) {
            throw new IllegalStateException("Cannot fail payment in final status: " + status);
        }
        this.errorMessage = errorMessage;
        setStatus(PaymentStatus.FAILED);
    }

    /**
     * Возвращает платеж
     */
    public void refund() {
        if (status != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Can only refund completed payments");
        }
        setStatus(PaymentStatus.REFUNDED);
    }

    /**
     * Отменяет платеж
     */
    public void cancel() {
        if (status.isFinal()) {
            throw new IllegalStateException("Cannot cancel payment in final status: " + status);
        }
        setStatus(PaymentStatus.CANCELLED);
    }

    /**
     * Проверяет, завершен ли платеж
     */
    public boolean isCompleted() {
        return status.isFinal();
    }

    /**
     * Проверяет, успешен ли платеж
     */
    public boolean isSuccessful() {
        return status.isSuccessful();
    }

    /**
     * Проверяет, можно ли вернуть платеж
     */
    public boolean isRefundable() {
        return status == PaymentStatus.COMPLETED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Payment{id='%s', order='%s', amount=%s, method='%s', status=%s}",
            id, order.getId(), amount, paymentMethod.getType(), status);
    }
}
