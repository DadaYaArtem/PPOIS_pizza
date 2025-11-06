package org.pizzeria.strategy;

import org.pizzeria.domain.common.Money;
import org.pizzeria.domain.payment.Payment;
import org.pizzeria.domain.payment.PaymentMethod;

/**
 * Интерфейс стратегии для обработки платежей.
 * Разные способы оплаты могут требовать разной логики обработки.
 */
public interface PaymentStrategy {
    /**
     * Обрабатывает платеж
     * @return true если платеж успешен, false иначе
     */
    boolean processPayment(Payment payment);

    /**
     * Проверяет, может ли стратегия обработать данный способ оплаты
     */
    boolean canProcess(PaymentMethod paymentMethod);

    /**
     * Возвращает описание стратегии
     */
    String getDescription();

    /**
     * Рассчитывает комиссию за обработку платежа (если есть)
     */
    Money calculateFee(Money amount);
}
