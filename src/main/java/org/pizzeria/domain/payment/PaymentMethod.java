package org.pizzeria.domain.payment;

/**
 * Интерфейс для способов оплаты.
 * Применяем полиморфизм - разные способы оплаты реализуют общий интерфейс.
 */
public interface PaymentMethod {
    /**
     * Возвращает тип способа оплаты
     */
    String getType();

    /**
     * Возвращает описание способа оплаты
     */
    String getDescription();

    /**
     * Проверяет, валиден ли способ оплаты
     */
    boolean isValid();

    /**
     * Маскирует чувствительные данные для отображения
     */
    String getMaskedInfo();
}
